package org.github.mybridge;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.github.mybridge.netty.NettyLauncher;

public class Mybridge {
	public static final String version = "0.0.1";
	public static final String name = "Mybridge";
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(Mybridge.class);

	public static void main(String args[]) {
		try {
			Properties properties = new Properties();
			properties.load(Mybridge.class.getClassLoader()
					.getResourceAsStream("log4j.properties"));
			PropertyConfigurator.configure(properties);
			// 处理main参数
			Parameter config = new Parameter();
			config.setIp("127.0.0.1");
			config.setPort(3307);
			// Launcher launcher = new MinaLauncher();
			Launcher launcher = new NettyLauncher(config);
			launcher.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}