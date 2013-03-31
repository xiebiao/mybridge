package com.github.mybridge.mina;

import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.github.mybridge.core.Handler;
import com.github.mybridge.core.MySQLCommandHandler;
import com.github.mybridge.core.MySQLCommands;
import com.github.mybridge.core.packet.AuthenticationPacket;
import com.github.mybridge.core.packet.CommandPacket;
import com.github.mybridge.core.packet.ErrorPacket;
import com.github.mybridge.core.packet.HandshakeState;
import com.github.mybridge.core.packet.InitialHandshakePacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;

public class MysqlServerHandler extends IoHandlerAdapter {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MysqlServerHandler.class);
	private HandshakeState state;
	private Handler handler;

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		byte[] bytes = (byte[]) message;
		String msg = "";
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
				// 编码
				if (MySQLCommands.index2Charset
						.containsKey((int) auth.charsetNum)) {
					handler.setCharset(MySQLCommands.index2Charset
							.get((int) auth.charsetNum));
				}
				// 取得schema
				if (auth.dbName.length() > 0) {
					String dbname = auth.dbName.substring(0,
							auth.dbName.length() - 1);
					handler.setDb(dbname);
				}
				logger.debug(auth.clientUser);
				// 验证用户名与密码
				if (auth.checkAuth(user, auth.clientPassword)) {
					OkPacket ok = new OkPacket();
					session.write(ok.getBytes());
					break;
				}
			} catch (Exception e) {
				logger.debug("packet auth failed  " + e);
				msg = "handshake authpacket failed  ";
				ErrorPacket errPacket = new ErrorPacket(1045, msg);
				session.write(errPacket.getBytes());
				state = HandshakeState.CLOSE;
				break;
			}
			msg = "Access denied for user " + auth.clientUser;
			ErrorPacket errPacket = new ErrorPacket(1045, msg);
			session.write(errPacket.getBytes());
			state = HandshakeState.CLOSE;
			break;
		case READ_COMMOND:
			state = HandshakeState.WRITE_RESULT;
			CommandPacket cmd = new CommandPacket();
			cmd.putBytes(bytes);
			List<Packet> resultlist = handler.execute(cmd);
			if (resultlist != null && resultlist.size() > 0) {
				writePacketList(session, resultlist);
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 
	 * @param session
	 * @param resultlist
	 */
	private void writePacketList(IoSession session, List<Packet> resultlist) {
		for (Packet packet : resultlist) {
			byte[] temp = packet.getBytes();
			session.write(temp);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);

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

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	/**
	 * handershake initpacket from server to client the first packet also called
	 * greet
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		Packet.setPacketId((byte) 0);
		state = HandshakeState.WRITE_INIT;
		handler = new MySQLCommandHandler();
		InitialHandshakePacket initPacket = new InitialHandshakePacket();
		byte[] temp = initPacket.getBytes();
		session.write(temp);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
	}

}
