package org.github.mybridge;

public class Configuration {
	private int port = 3307;
	private String ip;
	private boolean debug = false;

	public Configuration() {
		this.port = 3307;
		this.ip = "127.0.0.1";
		this.debug = false;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
