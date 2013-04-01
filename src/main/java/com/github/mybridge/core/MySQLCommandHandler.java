package com.github.mybridge.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.github.mybridge.core.packet.CommandPacket;
import com.github.mybridge.core.packet.EOFPacket;
import com.github.mybridge.core.packet.ErrorPacket;
import com.github.mybridge.core.packet.FieldPacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;
import com.github.mybridge.core.packet.ResultSetPacket;
import com.github.mybridge.core.packet.RowDataPacket;
import com.github.mybridge.engine.DbServer;
import com.github.mybridge.engine.DbServerFactory;
import com.github.mybridge.engine.DefaultGroup;

public class MySQLCommandHandler implements Handler {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLCommandHandler.class);
	// private String charset = "latin1";
	private String charset = "utf-8";
	private final static DbServer dbServer = DbServerFactory
			.getDbserver(new DefaultGroup(0));
	String db = "";

	public MySQLCommandHandler() {
		charset = "utf-8";
	}

	public List<Packet> execute(Packet packet) throws ExecuteException {
		List<Packet> packetList = null;
		CommandPacket cmdPacket = (CommandPacket) packet;
		try {
			packetList = new ArrayList<Packet>();
			if (cmdPacket.getType() == MySQLCommands.COM_QUERY) {
				String sql = new String(cmdPacket.getValue(), charset);
				logger.debug("COM_QUERY: " + sql);
				return executeSQL(sql);
			} else if (cmdPacket.getType() == MySQLCommands.COM_QUIT) {
				logger.debug("COM_QUIT ");
				return null;
			} else if (cmdPacket.getType() == MySQLCommands.COM_FIELD_LIST) {
				packetList.add(new EOFPacket());
				logger.debug("COM_FIELD_LIST ");
				return packetList;
			} else if (cmdPacket.getType() == MySQLCommands.COM_INIT_DB) {
				logger.debug("COM_INIT_DB ");
				String db = new String(cmdPacket.getValue(), charset);
				String sql = "USE" + db;
				setDb(db);
				return executeSQL(sql);
			}
			logger.warn("Error COM");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExecuteException("Command excute error");
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
		logger.debug(" execute sql");
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

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setDb(String db) {
		this.db = db;
	}

}
