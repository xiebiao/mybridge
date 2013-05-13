package com.github.mybridge.server;

import com.github.jnet.Configuration;
import com.github.jnet.Server;
import com.github.mybridge.Lifecycle;

public class JnetServer implements Lifecycle {

    Configuration          config;
    private JnetServerImpl server;

    public JnetServer() {
        server = new JnetServerImpl();
        config = new Configuration();
    }

    @Override
    public void start() {
        config.setIp("127.0.0.1");
        config.setPort(3307);
        try {
            server.init(config, JnetMySQLSession.class);
            server.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class JnetServerImpl extends Server<JnetMySQLSession> {

    }
}
