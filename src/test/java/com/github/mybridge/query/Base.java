package com.github.mybridge.query;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;

import com.alibaba.druid.pool.DruidDataSource;

public class Base {
	protected static DruidDataSource ds;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(Query.class);
	static {
		try {
			Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver")
					.newInstance();
			ds = new DruidDataSource();
			ds.setDriver(driver);
			ds.setUrl("jdbc:mysql://127.0.0.1:3307/wp");
			ds.setUsername("root");
			ds.setPassword("yes");
			ds.setTestOnBorrow(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void query(int i) throws Exception {
		Connection conn = ds.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from shard");
			while (rs.next()) {
				logger.info("select [" + i + "]:\t" + rs.getString(1) + " \t"
						+ rs.getString(2) + "\t" + rs.getString(3));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
