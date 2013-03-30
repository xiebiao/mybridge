package org.github.mybridge;

import org.github.mybridge.exception.ConfigurationException;

public interface Launcher {
	/**
	 * 启动
	 * 
	 */
	public void start();

	/**
	 * 初始化配置
	 * 
	 * @throws Exception
	 */
	public void init() throws ConfigurationException;

	public void stop();
}
