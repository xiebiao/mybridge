package com.github.mybridge.transport.jnet2;

import java.nio.channels.SocketChannel;

import com.github.jnet.AbstractConnection;
import com.github.jnet.AbstractConnectionFactory;

public class MySQLConnectionFactory extends AbstractConnectionFactory {

    @Override
    protected AbstractConnection getConnection(SocketChannel channel) {
        MySQLConnection mc = new MySQLConnection(channel);
        return mc;
    }

}
