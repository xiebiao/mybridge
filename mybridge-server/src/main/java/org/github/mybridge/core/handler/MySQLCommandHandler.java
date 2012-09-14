package org.github.mybridge.core.handler;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.github.mybridge.core.MySQLCommands;
import org.github.mybridge.core.handler.exception.CommandExecuteException;
import org.github.mybridge.core.packet.CommandPacket;
import org.github.mybridge.core.packet.EOFPacket;
import org.github.mybridge.core.packet.ErrorPacket;
import org.github.mybridge.core.packet.FieldPacket;
import org.github.mybridge.core.packet.OkPacket;
import org.github.mybridge.core.packet.Packet;
import org.github.mybridge.core.packet.ResultSetPacket;
import org.github.mybridge.core.packet.RowDataPacket;
import org.github.mybridge.engine.DbServer;
import org.github.mybridge.engine.DbServerFactory;
import org.github.mybridge.utils.CharsetUtils;

public class MySQLCommandHandler implements Handler {

	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	// private String charset = "latin1";
	private String charset = "utf-8";
	private final static DbServer dbServer = DbServerFactory.getDbserver("");
	String db = "";

	public MySQLCommandHandler() {
		charset = "utf-8";
	}

	public List<Packet> execute(Packet packet) throws CommandExecuteException {
		List<Packet> packetList = null;
		CommandPacket cmdPacket = (CommandPacket) packet;
		try {
			packetList = new ArrayList<Packet>();
			if (cmdPacket.getType() == MySQLCommands.COM_QUERY) {
				String sql = new String(cmdPacket.getValue(), charset);
				return executeSQL(sql);
			} else if (cmdPacket.getType() == MySQLCommands.COM_QUIT) {
				return null;
			} else if (cmdPacket.getType() == MySQLCommands.COM_FIELD_LIST) {
				packetList.add(new EOFPacket());
				return packetList;
			} else if (cmdPacket.getType() == MySQLCommands.COM_INIT_DB) {
				String db = new String(cmdPacket.getValue(), charset);
				String sql = "USE" + db;
				setDb(db);
				return executeSQL(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandExecuteException("Command excute error");
		}
		return packetList;
	}

	/**
	 * 执行Mysql Commond sql 如：SHOW SESSION VARIABLES,Show Collection,sql
	 * 
	 * @param sql
	 */
	private List<Packet> executeSQL(String sql) {
		List<Packet> packetList = new ArrayList<Packet>();
		try {
			packetList = execute(sql);
		} catch (Exception e) {
			packetList.add(new ErrorPacket());
		}
		return packetList;
	}

	private List<Packet> execute(String sql) throws SQLException {
		List<Packet> packetList = new ArrayList<Packet>();
		//
		// 解析SQL
		// 路由器
		//
		Connection connection = dbServer.getConnection();
		boolean result;
		Statement state;
		try {
			state = connection.createStatement();
			result = state.execute(sql);
		} catch (SQLException e) {
			ErrorPacket err = new ErrorPacket(e.getErrorCode(),
					e.getSQLState(), e.getMessage());
			packetList.add(err);
			return packetList;
		}
		if (result == false) {
			OkPacket ok = new OkPacket();
			ok.setAffectedRows(state.getUpdateCount());
			packetList.add(ok);
			return packetList;
		}
		ResultSet rs = state.getResultSet();
		ResultSetMetaData meta = rs.getMetaData();
		ResultSetPacket resultPacket = new ResultSetPacket(
				meta.getColumnCount());
		packetList.add(resultPacket);
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			FieldPacket fieldPacket = new FieldPacket();
			fieldPacket.setDb(meta.getCatalogName(i));
			fieldPacket.setTable(meta.getTableName(i));
			fieldPacket.setOrgTable(meta.getTableName(i));
			fieldPacket.setName(meta.getColumnName(i));
			fieldPacket.setOrgName(meta.getColumnName(i));
			fieldPacket.setType((byte) MySQLCommands.javaTypeToMysql(meta
					.getColumnType(i)));
			fieldPacket.setLength(meta.getColumnDisplaySize(i));
			packetList.add(fieldPacket);
		}
		packetList.add(new EOFPacket());
		while (rs.next()) {
			RowDataPacket rowPacket = new RowDataPacket(charset);
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String value = rs.getString(i);
				rowPacket.addValue(value);
			}
			packetList.add(rowPacket);
		}
		packetList.add(new EOFPacket());
		// rs.close();
		// state.close();
		// connection.close();
		// DbUtils.closeQuietly(connection, state, rs);
		return packetList;
	}

	public void open() {

	}

	public void close() {

	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setDb(String db) {
		this.db = db;
	}

}
