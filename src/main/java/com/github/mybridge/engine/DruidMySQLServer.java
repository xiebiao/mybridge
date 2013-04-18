package com.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidMySQLServer implements DatabaseServer {
	private static DruidDataSource ds;

	public DruidMySQLServer(JDBCProperties jdbc) {
		ds = new DruidDataSource();
		ds.setDriver(jdbc.getDriver());
		ds.setUrl(jdbc.getUrl());
		ds.setUsername(jdbc.getUser());
		ds.setPassword(jdbc.getPassword());
		ds.setTestOnBorrow(false);
		// ds.setMaxActive(50);
		// ds.setInitialSize(100);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public void stop() {
		ds.close();

	}

}
