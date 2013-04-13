package com.github.mybridge.jnet;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.github.jnet.IOState;
import com.github.jnet.utils.IOBuffer;
import com.github.mybridge.MySQLProtocol;
import com.github.mybridge.core.ExecuteException;
import com.github.mybridge.core.Handler;
import com.github.mybridge.core.MySQLCommands;
import com.github.mybridge.core.MySQLHandler;
import com.github.mybridge.core.packet.AuthenticationPacket;
import com.github.mybridge.core.packet.CommandPacket;
import com.github.mybridge.core.packet.ErrorPacket;
import com.github.mybridge.core.packet.HandshakeState;
import com.github.mybridge.core.packet.InitialHandshakePacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;
import com.github.mybridge.core.packet.PacketHeader;
import com.mysql.jdbc.StringUtils;

public class JnetMySQLProtocolImpl implements MySQLProtocol {
	private MysqlSession session;
	private HandshakeState state;
	private static Handler handler = new MySQLHandler();
	private byte packetId = 0;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(JnetMySQLProtocolImpl.class);

	public JnetMySQLProtocolImpl(MysqlSession session) {
		this.session = session;
		state = HandshakeState.WRITE_INIT;
	}

	@Override
	public void onSessionOpen(IOBuffer readBuffer, IOBuffer writeBuffer) {
		InitialHandshakePacket init = new InitialHandshakePacket();
		writePacket(writeBuffer, init);
	}

	@Override
	public void onPacketReceived(IOBuffer readBuffer, IOBuffer writeBuffer) {
		logger.debug("onPacketReceived:" + state);
		String msg = "";
		ErrorPacket errPacket = null;
		switch (state) {
		case READ_AUTH:
			logger.debug("onPacketReceived: XXXX");
			state = HandshakeState.WRITE_RESULT;
			AuthenticationPacket authPacket = new AuthenticationPacket();
			byte[] authByte = readBuffer.getBytes(0, readBuffer.limit());
			logger.debug(StringUtils.dumpAsHex(authByte, authByte.length));
			authPacket.putBytes(authByte);
			String user = "";
			if (authPacket.clientUser.length() > 1) {
				user = authPacket.clientUser.substring(0,
						authPacket.clientUser.length() - 1);
			}
			try {
				if (MySQLCommands.index2Charset
						.containsKey((int) authPacket.charsetNum)) {
					handler.setCharset(MySQLCommands.index2Charset
							.get((int) authPacket.charsetNum));
				}
				if (authPacket.dbName.length() > 0) {
					String dbname = authPacket.dbName.substring(0,
							authPacket.dbName.length() - 1);
					handler.setDatabaseName(dbname);
				}
				if (authPacket.checkAuth(user, authPacket.clientPassword)) {
					OkPacket okPacket = new OkPacket();
					this.writePacket(writeBuffer, okPacket);
					break;
				}
			} catch (Exception e) {
				msg = "handshake authpacket failed  ";
				errPacket = new ErrorPacket(1045, msg);
				this.writePacket(writeBuffer, errPacket);
				state = HandshakeState.CLOSE;
				break;
			}
			msg = "Access denied for user " + authPacket.clientUser;
			errPacket = new ErrorPacket(1045, msg);
			this.writePacket(writeBuffer, errPacket);
			state = HandshakeState.CLOSE;
			break;
		case READ_COMMOND:
			state = HandshakeState.WRITE_RESULT;
			CommandPacket cmdPacket = new CommandPacket();
			byte[] bytes = readBuffer.getBytes(0, readBuffer.limit());
			cmdPacket.putBytes(bytes);
			List<Packet> resultList = null;
			try {
				resultList = handler.execute(cmdPacket);
			} catch (ExecuteException e) {
				e.printStackTrace();
				errPacket = new ErrorPacket(1046, "server error");
				writePacket(writeBuffer, errPacket);
			}
			if (resultList != null && resultList.size() > 0) {
				writePacketList(writeBuffer, resultList);
			}// 如果为NULL，应该回写一个ErrorPacket

			break;
		default:
			break;
		}

	}

	@Override
	public void onPacketSended(IOBuffer readBuffer, IOBuffer writeBuffer) {
		switch (state) {
		case WRITE_INIT:
			logger.debug("onPacketSended case WRITE_INIT");
			state = HandshakeState.READ_AUTH;
			readPacket(readBuffer);
			break;
		case WRITE_RESULT:
			logger.debug("onPacketSended case WRITE_RESULT");
			state = HandshakeState.READ_COMMOND;
			readPacket(readBuffer);
			break;
		case CLOSE:
			this.session.setNextState(IOState.CLOSE);
		default:
			this.session.setNextState(IOState.CLOSE);
		}

	}

	@Override
	public void onSessionClose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPacketId(byte id) {
		this.packetId = id;

	}

	@Override
	public byte getPacketId() {
		return this.packetId;
	}

	private void readPacket(IOBuffer readBuf) {
		logger.debug("readPacket");
		readBuf.position(0);
		readBuf.limit(4);
		session.setNextState(IOState.READ);
	}

	private void writePacketList(IOBuffer writeBuf, List<Packet> resultlist) {
		writeBuf.position(0);
		for (Packet packet : resultlist) {
			byte[] body = packet.getBytes();
			PacketHeader header = new PacketHeader();
			header.setPacketLen(body.length);
			header.setPacketId(this.getPacketId());
			this.packetId++;
			writeBuf.writeBytes(header.getBytes());
			writeBuf.writeBytes(body);
		}
		writeBuf.limit(writeBuf.position());
		writeBuf.position(0);
		session.setNextState(IOState.WRITE);
	}

	private void writePacket(IOBuffer writeBuf, Packet packet) {
		byte[] body = packet.getBytes();
		PacketHeader header = new PacketHeader();
		header.setPacketLen(body.length);
		header.setPacketId(this.getPacketId());
		logger.info("packetId:" + header.getPacketId());
		writeBuf.position(0);
		writeBuf.writeBytes(header.getBytes());
		writeBuf.writeBytes(body);
		writeBuf.limit(writeBuf.position());
		writeBuf.position(0);
		// logger.debug(StringUtils.toString(body));
		this.session.setNextState(IOState.WRITE);
	}
}
