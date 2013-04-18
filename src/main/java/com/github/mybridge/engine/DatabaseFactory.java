package com.github.mybridge.engine;

public final class DatabaseFactory {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(DatabaseFactory.class);

	private DatabaseFactory() {
	}

	public static Database getMySQLServer(String database) {

		JDBCProperties jdbc = new JDBCProperties();
		jdbc.url = "jdbc:mysql://localhost:3306/wp";
		jdbc.user = "root";
		jdbc.password = "wangzhu";
		Database dbServer = new DefaultDatabase(jdbc);
		return dbServer;

	}
}
