package mybridge.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJdbc {
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3307/amesit", "root", "yes");
		for (int i = 0; i < 1; i++) {
			final Connection con = conn;
			//new Thread() {
				//public void run() {
					try {
						Statement stmt = con.createStatement();
						ResultSet rs = stmt
								.executeQuery("select * from city_info");
						while (rs.next()) {
							System.out.println(rs.getString("city_id") + ":"
									+ rs.getString("city_name"));
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			//}.start();
		//}

	}

}
