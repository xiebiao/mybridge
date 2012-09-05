package mybridge2.netty;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import mybridge2.packet.AuthenticationPacket;
import mybridge2.packet.ErrorPacket;
import mybridge2.packet.HandshakePacket;
import mybridge2.packet.MysqlResultSetPacket;
import mybridge2.packet.OkPacket;
import mybridge2.packet.Packet;
import mybridge2.packet.QueryCommandPacket;
import mybridge2.util.Constants;
import mybridge2.util.Security;
import mybridge2.util.Util;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.channel.Channel;

public class NetFSM implements Serializable {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public List<Packet> requests = null;

	HandshakePacket handshakePacket = null;

	private enum Status {
		STATE_SHAKEHAND, STATE_AUTH, STATE_CMD
	};

	private Status status = Status.STATE_SHAKEHAND;

	public NetFSM() {
		requests = new ArrayList<Packet>();
	}

	public List<Packet> getRequests() {
		return requests;
	}

	public void setRequests(List<Packet> requests) {
		this.requests = requests;
	}

	public void onConnect(Channel channel) {
		LOG.debug("NetFSM.onConnect:");
		handshakePacket = new HandshakePacket();
		handshakePacket.setProtocolVersion((byte) (10 & 0xff));
		handshakePacket.setServerVersion("5.5.10-rds");
		handshakePacket.setThreadId(Thread.currentThread().hashCode());
		handshakePacket.setSeed(Util.getNRandomString(8));
		handshakePacket.setServerCapabilities(0x800FF7FF); // : 填写对应的SERVER能力
		handshakePacket.setServerCharsetIndex((byte) 0x08); // : 字符集
		handshakePacket.setServerStatus((short) 2); // SERVER_STATUS_AUTOCOMMIT
		handshakePacket.setRestOfScrambleBuff(Util.getNRandomString(12));
		requests.add(handshakePacket);
		status = Status.STATE_AUTH;
		LOG.debug("NetFSM.onConnect END");
	}

	public void onMessage(Channel channel, PacketBuffer buffer) {
		LOG.debug("onMessage:");
		requests.clear();
		switch (status) {
		case STATE_AUTH:
			String serverpassword = Config.getConfig("password", "mysql");
			String serveruser = Config.getConfig("user", "mysql");
			AuthenticationPacket authPacket = new AuthenticationPacket(
					serverpassword);
			authPacket.init(buffer);
			boolean passwordchecked = false;
			authPacket.setSeed(handshakePacket.getSeed()
					+ handshakePacket.getRestOfScrambleBuff());
			if (!StringUtils.isEmpty(authPacket.getPassword())) {
				try {
					String encryptPassword = new String(Security.scramble411(
							authPacket.getPassword(), authPacket.getSeed()),
							Constants.ENCODING);
					passwordchecked = StringUtils.equals(
							new String(authPacket.getEncryptedPassword(),
									Constants.ENCODING), encryptPassword);
				} catch (UnsupportedEncodingException e) {
					LOG.error("UnsupportedEncoding", e);
				} catch (NoSuchAlgorithmException e) {
					LOG.error("NoSuchAlgorithmException", e);
				}
			}
			boolean userChecked = authPacket.getUser().equals(serveruser);
			LOG.debug(authPacket.toString());
			if (!userChecked) {
				// 鉴权失败，发送error给客户端。
				ErrorPacket errorPacket = new ErrorPacket();
				errorPacket.packetId = 1;
				errorPacket.setErrno(100);
				errorPacket.setServerErrorMessage("No Such User '"
						+ authPacket.getUser() + "'");
				requests.add(errorPacket);
				status = Status.STATE_AUTH;
				break;
			}
			if (passwordchecked) {
				// 鉴权成功,发送OK包给客户端。
				Packet okPacket = new OkPacket();
				okPacket.packetId = 2;
				requests.add(okPacket);
				status = Status.STATE_CMD;
			} else {
				// 鉴权失败，发送error给客户端。
				ErrorPacket errorPacket = new ErrorPacket();
				errorPacket.packetId = 1;
				errorPacket.setErrno(100);
				errorPacket.setServerErrorMessage("error password");
				requests.add(errorPacket);
				status = Status.STATE_AUTH;
			}
			break;
		case STATE_CMD:
			QueryCommandPacket commandPacket = new QueryCommandPacket();
			commandPacket.init(buffer);
			if (commandPacket.getCommand() != 3) {
				return;
			}
			// 获取得到待执行的sql语句.
			String sql = commandPacket.getQuery();
			LOG.debug("The original is : \t" + sql);
			String afterHandlerSql = sql;
			LOG.debug("The sql after handled becomes :" + afterHandlerSql);
			System.out.println(afterHandlerSql);
			if (afterHandlerSql.contains("SHOW VARIABLES")) {
				String[][] resultList = new String[][] { { "Variable_name",
						"Value" } };
				MysqlResultSetPacket mysqlResultSetPacket = new MysqlResultSetPacket(
						resultList);
				LOG.debug("Responses '" + afterHandlerSql + "'");
				requests.addAll(mysqlResultSetPacket.getPackets());
			}

			else if (afterHandlerSql
					.contains("session.auto_increment_increment")) {
				String[][] resultList = new String[][] {
						{ "@@session.auto_increment_increment" }, { "1" } };
				MysqlResultSetPacket mysqlResultSetPacket = new MysqlResultSetPacket(
						resultList);
				LOG.debug("Responses '" + afterHandlerSql + "'");
				requests.addAll(mysqlResultSetPacket.getPackets());
			} else if (afterHandlerSql.contains("SHOW COLLATION")) {
				String[][] resultList = new String[][] {
						{ "Collation", "Charset", "Id", "Default", "Compiled",
								"Sortlen" },
						{ "big5_chinese_ci", "big5", "1", "Yes", "Yes", "1" } };
				MysqlResultSetPacket mysqlResultSetPacket = new MysqlResultSetPacket(
						resultList);
				LOG.debug("Responses '" + afterHandlerSql + "'");
				requests.addAll(mysqlResultSetPacket.getPackets());
			} else if (afterHandlerSql.startsWith("SET")) {
				// 发送OK
				OkPacket okPacket2 = new OkPacket();
				okPacket2.packetId = 1;
				requests.add(okPacket2);
				LOG.debug("Responses '" + afterHandlerSql + "'");
			} else {
				// 根据执行的不同结果分别实例化MysqlResult(查询命令用到),OKPacket(执行非查询语句用到),ErrorPacket(执行非查询语句用到);
				// 这里把sql命令发送给dbgrid或者是master.
				// CommandResult commandResult = MonitoringCommand.command
				// .execute(afterHandlerSql);
				// if (commandResult.isSuccess()) {
				//
				// if (null != commandResult
				// && commandResult.getResultSet() != null
				// && commandResult.getResultSet().length == 0) {
				// Packet okPacket1 = new OkPacket();
				// okPacket1.packetId = 2;
				// requests.add(okPacket1);
				// StringBuffer stringBuffer = new StringBuffer(10);
				// stringBuffer.append("Execute command '")
				// .append(afterHandlerSql)
				// .append("' Successed,the execute sql is :\n")
				// .append(afterHandlerSql);
				// logger.debug(stringBuffer.toString());
				// requests.add(okPacket1);
				// }
				// // 查询语句。
				// else {
				// MysqlResultSetPacket mysqlResultSetPacket = new
				// MysqlResultSetPacket(
				// commandResult.getResultSet());
				// logger.debug("Query result is sending from server.");
				// requests.add(mysqlResultSetPacket);
				// }
				// } else {
				// ErrorPacket errorPacket = new ErrorPacket();
				// errorPacket.packetId = 1;
				// int errorCode = commandResult.getErrorCode();
				// String message = ErrorCodeParse.parse(commandResult
				// .getErrorCode());
				// errorPacket.setServerErrorMessage(message);
				// errorPacket.setErrno(commandResult.getErrorCode());
				// StringBuffer stringBuffer = new StringBuffer(10);
				// stringBuffer
				// .append("Execute command '")
				// .append(afterHandlerSql)
				// .append("' failed,the detail message are as follows :\n")
				// .append("ErrorCode: ").append(errorCode)
				// .append("\n Message: ").append(message);
				// logger.debug(stringBuffer.toString());
				// requests.add(errorPacket);
				// }
			}
			break;
		default:
			break;
		}
	}
}
