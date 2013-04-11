package com.github.mybridge;

public class MybridgeConfiguration {

	private String ip;
	private int port;
	private boolean debug;

	public MybridgeConfiguration() {

	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
