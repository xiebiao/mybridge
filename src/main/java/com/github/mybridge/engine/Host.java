package com.github.mybridge.engine;

public class Host {
	private String ip;
	private String port;

	public Host(String host) {
		int tag = host.indexOf(":");
		ip = host.substring(0, tag);
		port = host.substring(tag+1, host.length());
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String toString() {
		return ip + ":" + port;
	}
}
