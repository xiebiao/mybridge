package com.github.mybridge;

import com.github.jnet.Configuration;
import com.github.mybridge.exception.ConfigurationException;
import com.github.mybridge.jnet.JnetLauncher;

public final class DefaultLauncher implements Launcher {
	private Launcher launcher;
	public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(DefaultLauncher.class);
	private Configuration config;

	public DefaultLauncher(Configuration config) {
		this.config = config;
		launcher = new JnetLauncher(config);
		//launcher = new MinaLauncher(config);
		//launcher = new NettyLauncher(config);
	}

	@Override
	public void start() {
		launcher.start();
		logger.debug("Started " + config);
	}

	@Override
	public void init() throws ConfigurationException {
		launcher.init();

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
}
