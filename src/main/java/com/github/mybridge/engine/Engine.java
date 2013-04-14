package com.github.mybridge.engine;

public interface Engine {
	/**
	 * 加载路由配置
	 */
	public void load();

	public MySQLServer getServer(Group group, String sql);
}
