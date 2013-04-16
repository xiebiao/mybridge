package com.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidMySQLServer implements MySQLServer {
	private static DruidDataSource ds;

	public DruidMySQLServer(JDBCProperties jdbc) {
		ds = new DruidDataSource();
		ds.setDriver(jdbc.getDriver());
		ds.setUrl(jdbc.getUrl());
		ds.setUsername(jdbc.getUser());
		ds.setPassword(jdbc.getPassword());
		ds.setTestOnBorrow(false);
		//ds.setMaxActive(50);
		//ds.setInitialSize(100);
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return ds.getConnection();
	}

	@Override
	public boolean isMaster() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Group getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGroup(Group group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		ds.close();
	}

}
