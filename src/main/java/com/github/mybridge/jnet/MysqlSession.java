package com.github.mybridge.jnet;

import java.io.IOException;
import java.util.List;

import com.github.jnet.Session;
import com.github.jnet.utils.IOBuffer;
import com.github.jnet.utils.IOUtils;
import com.github.mybridge.core.ExecuteException;
import com.github.mybridge.core.Handler;
import com.github.mybridge.core.MySQLCommands;
import com.github.mybridge.core.MySQLHandler;
import com.github.mybridge.core.MySQLProtocol;
import com.github.mybridge.core.packet.AuthenticationPacket;
import com.github.mybridge.core.packet.CommandPacket;
import com.github.mybridge.core.packet.ErrorPacket;
import com.github.mybridge.core.packet.HandshakeState;
import com.github.mybridge.core.packet.InitialHandshakePacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;

public class MysqlSession extends Session {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MysqlSession.class);
	private HandshakeState state;
	private Handler handler;
	private MySQLProtocol mysql;
	private static final int BUF_SIZE = 2048;// (2M)

	public MysqlSession() {
		handler = new MySQLHandler();
		mysql = new MySQLProtocol();
	}

	@Override
	public void open(IOBuffer readBuf, IOBuffer writeBuf) throws Exception {
		if (readBuf.position() < 5) {
			state = HandshakeState.READ_AUTH;
			this.remainToRead(BUF_SIZE);
			return;
		}
		logger.debug(readBuf.toString());
		state = HandshakeState.WRITE_INIT;
		InitialHandshakePacket initPacket = new InitialHandshakePacket();
		// writeBuf.writeBytes(initPacket.getBytes());
		write(initPacket);
	}

	@Override
	public void readCompleted(IOBuffer readBuf, IOBuffer writeBuf)
			throws Exception {

	}

	@Override
	public void writeCompleted(IOBuffer readBuf, IOBuffer writeBuf)
			throws Exception {

	}

	@Override
	public void close() {

	}

	@Override
	public void reading(IOBuffer readBuf, IOBuffer writeBuf) throws Exception {
		String msg = "";
		ErrorPacket errPacket = null;
		switch (state) {
		case READ_AUTH:
			state = HandshakeState.WRITE_RESULT;
			AuthenticationPacket auth = new AuthenticationPacket();
			// auth.putBytes(bytes);

			String user = "";
			if (auth.clientUser.length() > 1) {
				user = auth.clientUser.substring(0,
						auth.clientUser.length() - 1);
			}
			try {
				if (MySQLCommands.index2Charset
						.containsKey((int) auth.charsetNum)) {
					handler.setCharset(MySQLCommands.index2Charset
							.get((int) auth.charsetNum));
				}
				if (auth.dbName.length() > 0) {
					String dbname = auth.dbName.substring(0,
							auth.dbName.length() - 1);
					handler.setDb(dbname);
					logger.debug("dbname name:" + dbname);
				}
				if (auth.checkAuth(user, auth.clientPassword)) {
					OkPacket okPacket = new OkPacket();

					write(okPacket);
					// channel.write(ok.getBytes());
					break;
				}
			} catch (Exception e) {
				msg = "handshake authpacket failed  ";
				errPacket = new ErrorPacket(1045, msg);

				write(errPacket);
				// channel.write(errPacket.getBytes());
				state = HandshakeState.CLOSE;
				break;
			}
			msg = "Access denied for user " + auth.clientUser;
			logger.debug(msg);
			errPacket = new ErrorPacket(1045, msg);

			write(errPacket);
			// channel.write(errPacket.getBytes());
			state = HandshakeState.CLOSE;
			break;
		case READ_COMMOND:
			state = HandshakeState.WRITE_RESULT;
			CommandPacket cmd = new CommandPacket();
			// cmd.putBytes(bytes);
			List<Packet> resultlist = null;
			try {
				resultlist = handler.execute(cmd);
			} catch (ExecuteException e) {
				e.printStackTrace();
				errPacket = new ErrorPacket(1046, "server error");

				write(errPacket);
				// channel.write(errPacket);
			}
			if (resultlist != null && resultlist.size() > 0) {
				// writePacketList(channel, resultlist);

			}
			break;
		default:
			break;
		}

	}

	private void write(Packet packet) {
		IOBuffer buf = new IOBuffer();
		buf.writeBytes(packet.getBytes());
		try {
			logger.debug(buf.toString());
			IOUtils.write(this.getSocket(), buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writing(IOBuffer readBuf, IOBuffer writeBuf) throws Exception {
		// TODO Auto-generated method stub

	}

}