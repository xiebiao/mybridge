package com.github.mybridge.engine;

public final class DbServerFactory {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(DbServerFactory.class);

	private DbServerFactory() {
	}

	public static DbServer getDbserver(Group group) {

		JDBCProperties jdbc = new JDBCProperties();
		jdbc.url = "jdbc:mysql://127.0.0.1:3306/wp";
		jdbc.user = "root";
		jdbc.password = "wangzhu";
		jdbc.driverName = "com.mysql.jdbc.Driver";
		DbServer dbServer = new DefaultDbServer(jdbc);
		return dbServer;

	}
}
