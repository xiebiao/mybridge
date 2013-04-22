package com.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

public class DefaultDatabase implements DatabaseServer {
	private static DruidDataSource ds;

	public DefaultDatabase() {

	}

	public DefaultDatabase(String host, String user, String password,
			String groupName) {

	}

	public DefaultDatabase(JDBCProperties jdbc) {
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
	public void shutdown() {
		ds.close();

	}

	@Override
	public Host getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

}
