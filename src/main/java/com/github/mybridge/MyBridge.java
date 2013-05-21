package com.github.mybridge;

import com.github.mybridge.config.ServerConfiguration;

public class MyBridge {
	public static final String version = "0.0.1";
	public static final String name = "Mybridge";

	public static void main(String args[]) {
		try {
			// 处理main参数
			ServerConfiguration config = new ServerConfiguration();
			config.setIp("127.0.0.1");
			config.setPort(3307);
			Launcher launcher = new DefaultLauncher(config);
			launcher.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}