package com.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

	public Connection getConnection() throws SQLException;

	public Host getHost();

	public boolean isAlive();

	public void shutdown();

}
