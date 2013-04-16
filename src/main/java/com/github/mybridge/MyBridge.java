package com.github.mybridge;

import com.github.jnet.Configuration;

public class MyBridge {
	public static final String version = "0.0.1";
	public static final String name = "Mybridge";

	public static void main(String args[]) {
		try {
			// 处理main参数
			Configuration config = new Configuration();
			config.setIp("127.0.0.1");
			config.setPort(3307);
			config.setMaxConnection(50);
			Launcher launcher = new DefaultLauncher(config);
			launcher.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}