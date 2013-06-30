package com.github.mybridge.transport.jnet;

import java.net.InetSocketAddress;

import com.github.jnet.SessionManager;
import com.github.mybridge.Launcher;
import com.github.mybridge.config.ServerConfiguration;

public class JnetLauncher implements Launcher {

    private JnetServer server;

    public JnetLauncher(ServerConfiguration config) {

    }

    @Override
    public void start() {
        server = new JnetServer(new InetSocketAddress("127.0.0.1", 3307));
        SessionManager sessionManager = new SessionManager();
        sessionManager.setHandler(JnetMySQLSession.class);
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
