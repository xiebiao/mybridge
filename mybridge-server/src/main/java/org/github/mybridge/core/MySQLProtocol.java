package org.github.mybridge.core;

import java.util.ArrayList;
import java.util.List;

import org.github.mybridge.core.handler.Handler;
import org.github.mybridge.core.handler.MysqlCommondHandler;
import org.github.mybridge.core.handler.exception.CommandException;
import org.github.mybridge.core.packet.AuthenticationPacket;
import org.github.mybridge.core.packet.CommandPacket;
import org.github.mybridge.core.packet.ErrorPacket;
import org.github.mybridge.core.packet.HandshakeState;
import org.github.mybridge.core.packet.InitialHandshakePacket;
import org.github.mybridge.core.packet.OkPacket;
import org.github.mybridge.core.packet.Packet;
import org.github.mybridge.utils.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

public class MySQLProtocol {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	private List<Packet> requests = null;
	private HandshakeState state;
	private Handler handler;

	public MySQLProtocol() {
		requests = new ArrayList<Packet>();
		handler = new MysqlCommondHandler();
	}

	public void onConnected(Channel channel) {
		state = HandshakeState.READ_AUTH;
		InitialHandshakePacket initPacket = new InitialHandshakePacket();
		LOG.debug(StringUtils.printHex(initPacket.getBytes()));
		channel.write(initPacket.getBytes());
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
				user = auth.clientUser.substring(0, auth.clientUser.length() - 1);
			}		
			try {
				// 编码
				if (MySQLCommand.index2Charset
						.containsKey((int) auth.charsetNum)) {
					handler.setCharset(MySQLCommand.index2Charset
							.get((int) auth.charsetNum));
				}
				// 取得schema
				if (auth.dbName.length() > 0) {
					String dbname = auth.dbName.substring(0,
							auth.dbName.length() - 1);
					handler.setDb(dbname);
				}
				// 验证用户名与密码
			
				if (auth.checkAuth(user, auth.clientPassword)) {
					OkPacket ok = new OkPacket();
					channel.write(ok.getBytes());
					LOG.debug(user);
					break;
				}
			} catch (Exception e) {
				LOG.debug("packet auth failed  " + e);
				msg = "handshake authpacket failed  ";
				errPacket = new ErrorPacket(1045, msg);
				channel.write(errPacket.getBytes());
				state = HandshakeState.CLOSE;
				break;
			}
			LOG.debug(StringUtils.printHex(auth.clientPassword));
			msg = "Access denied for user " + auth.clientUser;
			errPacket = new ErrorPacket(1045, msg);
			channel.write(errPacket.getBytes());
			state = HandshakeState.CLOSE;
			break;
		case READ_COMMOND:
			state = HandshakeState.WRITE_RESULT;
			CommandPacket cmd = new CommandPacket();
			cmd.putBytes(bytes);
			try {
				List<Packet> resultlist = handler.executeCommand(cmd);
			} catch (CommandException e) {
				e.printStackTrace();
				errPacket = new ErrorPacket(1046, "server error");
				channel.write(errPacket);
			}
			// if (resultlist != null && resultlist.size() > 0) {
			// writePacketList(session, resultlist);
			// }
			break;
		default:
			break;
		}
	}
}
