package com.github.mybridge.sharding;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 获取数据库连接
 * @author xiebiao
 */
public interface ConnectionPool {

    Connection getConnection() throws SQLException;
}
