package com.github.mybridge.engine;

public final class DatabaseServerFactory {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(DatabaseServerFactory.class);

	private DatabaseServerFactory() {
	}

	public static DatabaseServer getMySQLServer(String database) {

		JDBCProperties jdbc = new JDBCProperties();
		jdbc.url = "jdbc:mysql://localhost:3306/wp";
		jdbc.user = "root";
		jdbc.password = "wangzhu";
		DatabaseServer dbServer = new DruidMySQLServer(jdbc);
		return dbServer;

	}
}
