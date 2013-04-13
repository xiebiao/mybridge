package com.github.mybridge.jnet;

import com.github.jnet.Configuration;
import com.github.mybridge.Launcher;
import com.github.mybridge.exception.ConfigurationException;

public class JnetLauncher implements Launcher {
	Configuration config;
	MyBridgeServer server;

	public JnetLauncher(Configuration config) {
		this.config = config;
	}

	@Override
	public void start() {
		config.setIp("127.0.0.1");
		config.setPort(3307);
		server = new MyBridgeServer(config, MysqlSession.class);
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ConfigurationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
