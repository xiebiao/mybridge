package com.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseServer {
	Connection getConnection() throws SQLException;

	boolean isMaster();

	void stop();

}
