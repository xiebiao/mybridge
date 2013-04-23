package com.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseServer {

	public Connection getConnection() throws SQLException;

	public Address getAddress();

	public boolean isAlive();

	public void shutdown();

}
