package com.github.mybridge;

import com.github.mybridge.exception.ConfigurationException;
import com.github.mybridge.mina.MinaLauncher;

public final class DefaultLauncher implements Launcher {
	private Launcher launcher;

	public DefaultLauncher(Configuration config) {
		launcher = new MinaLauncher(config);
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
