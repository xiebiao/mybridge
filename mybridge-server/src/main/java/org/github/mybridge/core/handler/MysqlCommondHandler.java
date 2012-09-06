package org.github.mybridge.core.handler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.github.mybridge.core.MysqlCommand;
import org.github.mybridge.core.packet.Packet;
import org.github.mybridge.core.packet.CommandPacket;
import org.github.mybridge.core.packet.EOFPacket;
import org.github.mybridge.core.packet.ErrorPacket;
import org.github.mybridge.core.packet.FieldPacket;
import org.github.mybridge.core.packet.OkPacket;
import org.github.mybridge.core.packet.ResultSetPacket;
import org.github.mybridge.core.packet.RowDataPacket;
import org.github.mybridge.engine.DbServer;
import org.github.mybridge.engine.DbServerFactory;

public class MysqlCommondHandler implements Handler {

	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	// private String charset = "latin1";
	private String charset = "utf-8";
	private final static DbServer dbServer = DbServerFactory.getDbserver("");
	String db = "";

	public List<Packet> executeCommand(CommandPacket cmd) throws Exception {
		List<Packet> packetList = new ArrayList<Packet>();
		if (cmd.type == MysqlCommand.COM_QUERY) {
			String sql = new String(cmd.value, charset);
			return executeSQL(sql);
		} else if (cmd.type == MysqlCommand.COM_QUIT) {
			return null;
		} else if (cmd.type == MysqlCommand.COM_FIELD_LIST) {
			packetList.add(new EOFPacket());
			return packetList;
		} else if (cmd.type == MysqlCommand.COM_INIT_DB) {
			String db = new String(cmd.value, charset);
			LOG.debug("com init db " + db);
			String sql = "USE" + db;
			setDb(db);
			return executeSQL(sql);
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
		// Connection con= MysqlUtil.getConnection();
		// GenericObjectPool<Connection> pool = ServerConnectionPool.poolMap
		// .get("db1");
		Connection connection = dbServer.getConnection();
		// try {
		// con = pool.borrowObject();
		// } catch (Exception e1) {
		// LOG.debug("abtain connection faild : ", e1);
		// } finally {
		// try {
		// pool.returnObject(con);
		// } catch (Exception e) {
		// LOG.debug("return connection to pool faild: ", e);
		// }
		// }
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
			ok.affectedRows = state.getUpdateCount();
			packetList.add(ok);
			return packetList;
		}
		ResultSet rs = state.getResultSet();
		ResultSetMetaData meta = rs.getMetaData();
		ResultSetPacket resultPacket = new ResultSetPacket();
		resultPacket.fieldCount = meta.getColumnCount();
		packetList.add(resultPacket);
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			FieldPacket fieldPacket = new FieldPacket();
			fieldPacket.db = meta.getCatalogName(i);
			fieldPacket.table = meta.getTableName(i);
			fieldPacket.orgTable = meta.getTableName(i);
			fieldPacket.name = meta.getColumnName(i);
			fieldPacket.orgName = meta.getColumnName(i);
			fieldPacket.type = (byte) MysqlCommand.javaTypeToMysql(meta
					.getColumnType(i));
			fieldPacket.length = meta.getColumnDisplaySize(i);
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
		DbUtils.closeQuietly(connection, state, rs);
		return packetList;
	}

	public void open() {

	}

	public void close() {

	}

	public void setCharset(String charset) throws Exception {
		this.charset = charset;
	}

	public void setDb(String db) throws Exception {
		this.db = db;
	}

}
