package com.github.mybridge;

import com.github.mybridge.exception.ConfigException;

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
	public void init() throws ConfigException;

	public void stop();
}
