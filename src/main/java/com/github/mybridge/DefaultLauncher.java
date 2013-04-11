package com.github.mybridge;

import com.github.mybridge.exception.ConfigurationException;
import com.github.mybridge.netty.NettyLauncher;

public final class DefaultLauncher implements Launcher {
	private Launcher launcher;

	public DefaultLauncher(com.github.jnet.Configuration config) {
		//launcher = new JnetLauncher(config);
		launcher = new NettyLauncher(config);
	}

	@Override
	public void start() {
		launcher.start();

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
