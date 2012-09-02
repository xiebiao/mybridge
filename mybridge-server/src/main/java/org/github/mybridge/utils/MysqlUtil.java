package org.github.mybridge.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlUtil {

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/test", "root", "123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
