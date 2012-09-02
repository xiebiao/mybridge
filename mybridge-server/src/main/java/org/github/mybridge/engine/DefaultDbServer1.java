package org.github.mybridge.engine;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DefaultDbServer1 implements DbServer {
	private JDBCProperties jdbc;
	private static ComboPooledDataSource pool;

	public DefaultDbServer1(JDBCProperties jdbc) {
		pool = new ComboPooledDataSource();
		try {
			pool.setDriverClass(jdbc.getDriverName()); // loads the jdbc driver
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.setJdbcUrl(jdbc.getUrl());
		pool.setUser(jdbc.getUser());
		pool.setPassword(jdbc.getPassword());
		pool.setMaxStatements( 180 ); 
		pool.setMinPoolSize(10);
		pool.setMaxPoolSize(50);
	}

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return pool.getConnection();
	}

	public void destroy() {
		pool.close();
	}

}
