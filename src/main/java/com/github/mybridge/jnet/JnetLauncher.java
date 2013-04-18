package com.github.mybridge.jnet;

import com.github.jnet.Configuration;
import com.github.mybridge.Launcher;
import com.github.mybridge.exception.ConfigException;

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
		server = new MyBridgeServer(config, MySQLSession.class);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ConfigException {

	}

	@Override
	public void stop() {

	}

}
