package mybridge.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.alibaba.druid.pool.DruidDataSource;

public class TestJdbc {
	private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(TestJdbc.class);
	private static DruidDataSource ds;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		int count = 10;
		while (count-- > 0) {
			query(count);
		}
	}

	public static void query(int i) throws Exception {
		Connection conn = ds.getConnection();
		try {
			LOG.debug("查询结果");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from wp_users");
			while (rs.next()) {
				LOG.debug("select " + i + " :" + rs.getString(1) + " "
						+ rs.getString(2) + " " + rs.getString(3));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
