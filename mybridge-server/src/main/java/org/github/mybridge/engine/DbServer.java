package org.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbServer {
	Connection getConnection() throws SQLException;

	void destroy();
}
