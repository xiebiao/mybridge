package org.github.mybridge;

import org.github.mybridge.exception.ConfigurationException;
import org.github.mybridge.netty.NettyLauncher;

public final class DefaultLauncher implements Launcher {
	private Launcher launcher;

	public DefaultLauncher(Configuration config) {
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
