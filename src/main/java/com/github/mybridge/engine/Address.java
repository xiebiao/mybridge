package com.github.mybridge.engine;

public class Address {
	private String ip;
	private int port;

	public Address(String ip, int port) {
		this.ip = ip;
		this.port = port;
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
	public String toString() {
		return ip + ":" + port;
	}
}
