package mybridge.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJdbc {
	private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(TestJdbc.class);

	public static void main(String[] args) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3307/wp", "root", "yes");
		try {
			LOG.debug("查询结果");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from wp_users");
			while (rs.next()) {
				LOG.debug(rs.getString(1) + " " + rs.getString(2) + " "
						+ rs.getString(3));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
