package org.github.mybridge;

import java.util.Properties;


import org.apache.log4j.PropertyConfigurator;
import org.github.mybridge.launch.Launcher;
import org.github.mybridge.plugin.mina.MinaLauncher;

public class Mybridge {
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(Mybridge.class);

	public static void main(String args[]) {
		try {
			boolean debug = true;
			Properties properties = new Properties();
			properties.load(Mybridge.class.getClassLoader()
					.getResourceAsStream("log4j.properties"));
			PropertyConfigurator.configure(properties);
			LOG.debug("startup...");
			// 处理main参数
			Configuration config = new Configuration();
			config.setIp("127.0.0.1");
			config.setPort(3307);
			config.setDebug(debug);
			Launcher launcher = new MinaLauncher();
			launcher.start(config);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}