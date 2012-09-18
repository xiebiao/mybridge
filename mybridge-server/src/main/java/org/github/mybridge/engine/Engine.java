package org.github.mybridge.engine;

public interface Engine {
	/**
	 * 加载路由配置
	 */
	public void load();

	public DbServer getServer(Group group, String sql);
}
