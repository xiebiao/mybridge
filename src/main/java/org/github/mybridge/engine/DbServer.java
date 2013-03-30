package org.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbServer {
	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	Connection getConnection() throws SQLException;

	/**
	 * 是否是master
	 * 
	 * @return
	 */
	boolean isMaster();

	/**
	 * 关闭连接池
	 */
	void destroy();
}
