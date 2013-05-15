package com.github.mybridge.server;

import com.github.jnet.Configuration;
import com.github.mybridge.Launcher;

public class JnetLauncher implements Launcher {

    Configuration      config;
    JnetMyBridgeServer server;

    public JnetLauncher(Configuration config) {
        this.config = config;
    }

    @Override
    public void start() {
        config.setIp("127.0.0.1");
        config.setPort(3307);
        server = new JnetMyBridgeServer();
        try {
            server.init(config, JnetMySQLSession.class);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {

    }

}
