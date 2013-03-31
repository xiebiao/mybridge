package com.github.mybridge.engine;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DefaultDbServer implements DbServer {
	private JDBCProperties jdbc;
	private static BoneCP pool;

	public DefaultDbServer(JDBCProperties jdbc) {
		if (jdbc == null) {
			throw new java.lang.IllegalArgumentException();
		}
		this.jdbc = jdbc;
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(this.jdbc.getUrl()); // jdbc url specific to your
												// database, eg
												// jdbc:mysql://127.0.0.1/yourdb
		config.setUsername(this.jdbc.getUser());
		config.setPassword(this.jdbc.getPassword());
		config.setMinConnectionsPerPartition(10);
		config.setMaxConnectionsPerPartition(50);
		try {
			pool = new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public JDBCProperties getJdbc() {
		return jdbc;
	}

	public Connection getConnection() throws SQLException {
		return pool.getConnection();
	}

	public void destroy() {
		pool.shutdown();
	}

	@Override
	public boolean isMaster() {
		// TODO Auto-generated method stub
		return false;
	}

}
