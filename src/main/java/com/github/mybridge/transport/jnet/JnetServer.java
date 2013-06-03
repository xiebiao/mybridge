package com.github.mybridge.transport.jnet;

import java.net.InetSocketAddress;

public class JnetServer extends com.github.jnet.AbstractServer implements com.github.mybridge.server.Server {

    public JnetServer(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

}
