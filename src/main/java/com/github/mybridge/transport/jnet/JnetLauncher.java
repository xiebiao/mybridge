package com.github.mybridge.transport.jnet;

import com.github.jnet.SessionManager;
import com.github.mybridge.Launcher;
import com.github.mybridge.config.ServerConfiguration;

public class JnetLauncher implements Launcher {

    private ServerConfiguration config;
    private JnetServer          server;

    public JnetLauncher(ServerConfiguration config) {
        this.config = config;
    }

    @Override
    public void start() {
        config.setIp("127.0.0.1");
        config.setPort(3307);
        server = new JnetServer();
        SessionManager sessionManager = new SessionManager(JnetMySQLSession.class);
        try {
            server.init(sessionManager);
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
