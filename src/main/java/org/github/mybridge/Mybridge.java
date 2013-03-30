package org.github.mybridge;


public class Mybridge {
	public static final String version = "0.0.1";
	public static final String name = "Mybridge";
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(Mybridge.class);

	public static void main(String args[]) {
		try {
			// 处理main参数
			Configuration config = new Configuration();
			config.setIp("127.0.0.1");
			config.setPort(3307);
			// Launcher launcher = new MinaLauncher();
			Launcher launcher = new DefaultLauncher(config);
			launcher.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}