package com.github.mybridge;


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
	public void init() ;

	public void stop();
}
