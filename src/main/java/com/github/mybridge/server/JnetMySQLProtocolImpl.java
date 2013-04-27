package com.github.mybridge.server;

import java.util.List;

import com.github.jnet.Session.IoState;
import com.github.jnet.utils.IoBuffer;
import com.github.mybridge.core.ExecuteException;
import com.github.mybridge.core.Handler;
import com.github.mybridge.core.MySQLCommandPhase;
import com.github.mybridge.core.MySQLHandler;
import com.github.mybridge.core.MySQLProtocol;
import com.github.mybridge.core.packet.AuthenticationPacket;
import com.github.mybridge.core.packet.CommandsPacket;
import com.github.mybridge.core.packet.ErrPacket;
import com.github.mybridge.core.packet.HandshakeState;
import com.github.mybridge.core.packet.InitialHandshakePacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;
import com.github.mybridge.core.packet.PacketHeader;
import com.mysql.jdbc.StringUtils;

public class JnetMySQLProtocolImpl implements MySQLProtocol {
	private JnetMySQLSession session;
	private HandshakeState state;
	private static Handler handler = new MySQLHandler();
	// private static Handler handler = new TestHandler();
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(JnetMySQLProtocolImpl.class);

	public JnetMySQLProtocolImpl(JnetMySQLSession session) {
		this.session = session;
		state = HandshakeState.WRITE_INIT;
	}

	@Override
	public void connected(IoBuffer readBuffer, IoBuffer writeBuffer) {
		InitialHandshakePacket init = new InitialHandshakePacket();
		init.packetNumberInc();
		writePacket(writeBuffer, init);
	}

	@Override
	public void packetReceived(IoBuffer readBuffer, IoBuffer writeBuffer) {
		String msg = "";
		ErrPacket errPacket = null;
		switch (state) {
		case READ_AUTH:
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
				if (MySQLCommandPhase.index2Charset
						.containsKey((int) authPacket.charsetNum)) {
					handler.setCharset(MySQLCommandPhase.index2Charset
							.get((int) authPacket.charsetNum));
				}
				if (authPacket.databaseName.length() > 0) {
					String dbname = authPacket.databaseName.substring(0,
							authPacket.databaseName.length() - 1);
					handler.setDatabase(dbname);
				}
				if (authPacket.checkAuth(user, authPacket.clientPassword)) {
					OkPacket okPacket = new OkPacket();
					this.writePacket(writeBuffer, okPacket);
					break;
				}
			} catch (Exception e) {
				msg = "handshake authpacket failed  ";
				errPacket = new ErrPacket(1045, msg);
				this.writePacket(writeBuffer, errPacket);
				state = HandshakeState.CLOSE;
				break;
			}
			msg = "Access denied for user " + authPacket.clientUser;
			errPacket = new ErrPacket(1045, msg);
			this.writePacket(writeBuffer, errPacket);
			state = HandshakeState.CLOSE;
			break;
		case READ_COMMOND:
			state = HandshakeState.WRITE_RESULT;
			CommandsPacket cmdPacket = new CommandsPacket();
			byte[] bytes = readBuffer.getBytes(0, readBuffer.limit());
			cmdPacket.putBytes(bytes);
			List<Packet> resultList = null;
			try {
				resultList = handler.execute(cmdPacket);
			} catch (ExecuteException e) {
				e.printStackTrace();
				errPacket = new ErrPacket(1046, "server error");
				writePacket(writeBuffer, errPacket);
			}
			if (resultList != null && resultList.size() > 0) {
				writePacketList(writeBuffer, resultList);
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void packetSended(IoBuffer readBuffer, IoBuffer writeBuffer) {
		switch (state) {
		case WRITE_INIT:
			state = HandshakeState.READ_AUTH;
			readPacket(readBuffer);
			break;
		case WRITE_RESULT:
			state = HandshakeState.READ_COMMOND;
			readPacket(readBuffer);
			break;
		case CLOSE:
			break;
		default:
		}

	}

	private void readPacket(IoBuffer readBuf) {
		readBuf.position(0);
		readBuf.limit(4);
		session.setNextState(IoState.READ);
	}

	private void writePacketList(IoBuffer writeBuf, List<Packet> resultlist) {
		writeBuf.position(0);
		for (Packet packet : resultlist) {
			byte[] body = packet.getBytes();
			PacketHeader header = new PacketHeader();
			header.setPacketLen(body.length);
			header.packetNumberInc();
			writeBuf.writeBytes(header.getBytes());
			writeBuf.writeBytes(body);
		}
		writeBuf.limit(writeBuf.position());
		writeBuf.position(0);
		session.setNextState(IoState.WRITE);
	}

	private void writePacket(IoBuffer writeBuf, Packet packet) {
		byte[] body = packet.getBytes();
		PacketHeader header = new PacketHeader();
		header.setPacketLen(body.length);
		header.packetNumberInc();
		logger.debug("packetId:" + header.getPacketNumber());
		writeBuf.position(0);
		writeBuf.writeBytes(header.getBytes());
		writeBuf.writeBytes(body);
		writeBuf.limit(writeBuf.position());
		writeBuf.position(0);
		// logger.debug(StringUtils.toString(body));
		this.session.setNextState(IoState.WRITE);
	}
}
