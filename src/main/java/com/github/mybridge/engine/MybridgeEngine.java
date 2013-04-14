package com.github.mybridge.engine;

import java.util.HashMap;
import java.util.Map;

public class MybridgeEngine implements Engine {
	/* key: */
	private static final Map<String, MySQLServer> servers = new HashMap<String, MySQLServer>();

	@Override
	public void load() {

	}

	@Override
	public MySQLServer getServer(Group group, String sql) {
		Parser parser = new SQLParser();
		SQL s = parser.parse(sql);
		// 从servers中获取server
		return null;
	}

}
