package com.github.mybridge.sharding;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    Connection getConnection() throws SQLException;
}
