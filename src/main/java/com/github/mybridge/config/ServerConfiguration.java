package com.github.mybridge.config;

public class ServerConfiguration extends Configuration {

    private String ip;
    private int    port;

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
        return "{ip:" + this.ip + ", port:" + this.port + "}";
    }
}
