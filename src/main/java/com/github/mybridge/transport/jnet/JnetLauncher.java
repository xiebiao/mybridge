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
<<<<<<< HEAD
        config.setIp("127.0.0.1");
        config.setPort(3307);
        server = new JnetServer(new InetSocketAddress("127.0.0.1",3307));
=======
        server = new JnetServer(new InetSocketAddress("127.0.0.1", 3307));
>>>>>>> 203c5db06dbd861f9294906f9a984a048d16f9f5
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
