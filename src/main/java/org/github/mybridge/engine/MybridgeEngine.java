package org.github.mybridge.engine;

import java.util.HashMap;
import java.util.Map;

public class MybridgeEngine implements Engine {
	/* key: */
	private static final Map<String, DbServer> servers = new HashMap<String, DbServer>();

	@Override
	public void load() {

	}

	@Override
	public DbServer getServer(Group group, String sql) {
		Parser parser = new SQLParser();
		SQL s = parser.parse(sql);
		// 从servers中获取server
		return null;
	}

}
