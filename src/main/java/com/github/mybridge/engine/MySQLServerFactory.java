package com.github.mybridge.engine;

public final class MySQLServerFactory {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLServerFactory.class);

	private MySQLServerFactory() {
	}

	public static MySQLServer getMySQLServer(Group group) {

		JDBCProperties jdbc = new JDBCProperties();
		jdbc.url = "jdbc:mysql://localhost:3306/wp";
		jdbc.user = "root";
		jdbc.password = "wangzhu";
		MySQLServer dbServer = new DruidMySQLServer(jdbc);
		return dbServer;

	}
}
