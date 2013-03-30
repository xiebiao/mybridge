package mybridge.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJdbc {
	private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(TestJdbc.class);

	public static void main(String[] args) throws Exception {
		LOG.debug("xxx1");
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/amesit", "root", "yaes");
		try {
			LOG.debug("xxx2");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from city_info");
			while (rs.next()) {
				LOG.debug(rs.getString("city_id")
						+ "\n:"
						+ rs.getString("city_name")
						+ ":\n"
						+ new String(rs.getString("city_name").getBytes(
								"iso-8859-1"), "GBK"));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
