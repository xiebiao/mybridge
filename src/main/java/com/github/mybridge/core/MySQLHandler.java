package com.github.mybridge.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.github.mybridge.core.packet.CommandsPacket;
import com.github.mybridge.core.packet.EofPacket;
import com.github.mybridge.core.packet.ErrPacket;
import com.github.mybridge.core.packet.FieldDescriptionPacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;
import com.github.mybridge.core.packet.ResultSetPacket;
import com.github.mybridge.core.packet.RowDataPacket;

public class MySQLHandler implements Handler {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLHandler.class);
	private String charset = "utf-8";
	private String database = "";
	public MySQLHandler() {
		charset = "utf-8";
		//databases.add(DatabaseServerFactory.getMySQLServer(database));
	}

	public List<Packet> execute(Packet packet) throws ExecuteException {
		List<Packet> packetList = null;
		CommandsPacket cmdPacket = (CommandsPacket) packet;
		int cmdType = cmdPacket.getType();
		try {
			packetList = new ArrayList<Packet>();
			switch (cmdType) {
			case MySQLCommandPhase.COM_QUERY:
				String sql = new String(cmdPacket.getValue(), charset);
				logger.debug("COM_QUERY: " + sql);
				return executeSQL(sql);
			case MySQLCommandPhase.COM_QUIT:
				return null;
			case MySQLCommandPhase.COM_FIELD_LIST:
				packetList.add(new EofPacket());
				return packetList;
			case MySQLCommandPhase.COM_INIT_DB:
				String db = new String(cmdPacket.getValue(), charset);
				sql = "USE" + db;
				logger.debug("COM_INIT_DB: " + db);
				setDatabase(db);
				return executeSQL(sql);
			}
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
			packetList.add(new ErrPacket());
		}
		return packetList;
	}

	private List<Packet> execute(String sql) throws SQLException {
		List<Packet> packetList = new ArrayList<Packet>();
		//
		// 解析SQL
		// 路由器
		//
		// if (databases == null || databases.size() == 0) {
		// throw new SQLException("Can't find database.");
		// }
		//Connection connection = databases.get(0).getConnection();
		Connection connection = null;
		boolean result;
		Statement state;
		try {
			state = connection.createStatement();
			result = state.execute(sql);
		} catch (SQLException e) {
			ErrPacket err = new ErrPacket(e.getErrorCode(), e.getSQLState(),
					e.getMessage());
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
			FieldDescriptionPacket fieldPacket = new FieldDescriptionPacket();
			fieldPacket.setDatabase(meta.getCatalogName(i));
			fieldPacket.setTable(meta.getTableName(i));
			fieldPacket.setOrgTable(meta.getTableName(i));
			fieldPacket.setName(meta.getColumnName(i));
			fieldPacket.setOrgName(meta.getColumnName(i));
			fieldPacket.setType((byte) MySQLCommandPhase.javaTypeToMysql(meta
					.getColumnType(i)));
			fieldPacket.setLength(meta.getColumnDisplaySize(i));
			packetList.add(fieldPacket);
		}
		packetList.add(new EofPacket());
		while (rs.next()) {
			RowDataPacket rowPacket = new RowDataPacket(charset);
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String value = rs.getString(i);
				rowPacket.addValue(value);
			}
			packetList.add(rowPacket);
		}
		packetList.add(new EofPacket());
		rs.close();
		state.close();
		connection.close();
		DbUtils.closeQuietly(connection, state, rs);
		return packetList;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setDatabase(String db) {
		this.database = db;
	}

}
