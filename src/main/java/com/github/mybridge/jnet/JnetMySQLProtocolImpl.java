package com.github.mybridge.jnet;

import java.util.List;

import com.github.jnet.IOState;
import com.github.jnet.utils.IOBuffer;
import com.github.mybridge.MySQLProtocol;
import com.github.mybridge.core.ExecuteException;
import com.github.mybridge.core.Handler;
import com.github.mybridge.core.MySQLCommandPhase;
import com.github.mybridge.core.MySQLHandler;
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
	private MySQLSession session;
	private HandshakeState state;
	private static Handler handler = new MySQLHandler();
	// private static Handler handler = new TestHandler();

	private byte packetNumber = 0;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(JnetMySQLProtocolImpl.class);

	public JnetMySQLProtocolImpl(MySQLSession session) {
		this.session = session;
		state = HandshakeState.WRITE_INIT;
	}

	@Override
	public void connected(IOBuffer readBuffer, IOBuffer writeBuffer) {
		InitialHandshakePacket init = new InitialHandshakePacket();
		init.setPacketNumber((byte) (packetNumber + 1));
		writePacket(writeBuffer, init);
	}

	@Override
	public void packetReceived(IOBuffer readBuffer, IOBuffer writeBuffer) {
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
	public void packetSended(IOBuffer readBuffer, IOBuffer writeBuffer) {
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

	@Override
	public void close() {
		this.session.setNextState(IOState.CLOSE);
	}

	@Override
	public byte getPacketNumber() {
		return this.packetNumber;
	}

	private void readPacket(IOBuffer readBuf) {
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
			header.setPacketNumber(this.packetNumber);
			this.packetNumber++;
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
		header.setPacketNumber(this.packetNumber);
		logger.debug("packetId:" + header.getPacketNumber());
		writeBuf.position(0);
		writeBuf.writeBytes(header.getBytes());
		writeBuf.writeBytes(body);
		writeBuf.limit(writeBuf.position());
		writeBuf.position(0);
		// logger.debug(StringUtils.toString(body));
		this.session.setNextState(IOState.WRITE);
	}
}
