package com.github.mybridge.core;

import java.util.List;

import org.jboss.netty.channel.Channel;

import com.github.mybridge.core.packet.AuthenticationPacket;
import com.github.mybridge.core.packet.CommandPacket;
import com.github.mybridge.core.packet.ErrorPacket;
import com.github.mybridge.core.packet.HandshakeState;
import com.github.mybridge.core.packet.InitialHandshakePacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;

public class MySQLProtocol {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLProtocol.class);
	private HandshakeState state;
	private Handler handler;

	public MySQLProtocol() {
		handler = new MySQLHandler();
	}

	public void onConnected(Channel channel) {
		logger.debug(channel.toString());
		state = HandshakeState.WRITE_INIT;
		InitialHandshakePacket initPacket = new InitialHandshakePacket();
		channel.write(initPacket.getBytes());
	}

	public void writeCompleted() {
		switch (state) {
		case WRITE_INIT:
			state = HandshakeState.READ_AUTH;
			break;
		case WRITE_RESULT:
			state = HandshakeState.READ_COMMOND;
		case CLOSE:
		default:
			break;
		}
	}

	public void onRequestReceived(Channel channel, byte[] bytes) {
		String msg = "";
		ErrorPacket errPacket = null;
		switch (state) {
		case READ_AUTH:
			state = HandshakeState.WRITE_RESULT;
			AuthenticationPacket auth = new AuthenticationPacket();
			auth.putBytes(bytes);

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
					OkPacket ok = new OkPacket();
					channel.write(ok.getBytes());
					break;
				}
			} catch (Exception e) {
				msg = "handshake authpacket failed  ";
				errPacket = new ErrorPacket(1045, msg);
				channel.write(errPacket.getBytes());
				state = HandshakeState.CLOSE;
				break;
			}
			msg = "Access denied for user " + auth.clientUser;
			logger.debug(msg);
			errPacket = new ErrorPacket(1045, msg);
			channel.write(errPacket.getBytes());
			state = HandshakeState.CLOSE;
			break;
		case READ_COMMOND:
			state = HandshakeState.WRITE_RESULT;
			CommandPacket cmd = new CommandPacket();
			cmd.putBytes(bytes);
			List<Packet> resultlist = null;
			try {
				resultlist = handler.execute(cmd);
			} catch (ExecuteException e) {
				e.printStackTrace();
				errPacket = new ErrorPacket(1046, "server error");
				channel.write(errPacket);
			}
			if (resultlist != null && resultlist.size() > 0) {
				writePacketList(channel, resultlist);
			}
			break;
		default:
			break;
		}
	}

	private void writePacketList(Channel channel, List<Packet> resultlist) {
		for (Packet packet : resultlist) {
			byte[] temp = packet.getBytes();
			channel.write(temp);
		}
	}
}
