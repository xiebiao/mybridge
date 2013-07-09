package com.github.mybridge.transport.jnet2;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.github.jnet.AbstractConnectionFactory;
import com.github.jnet.IoAcceptor;
import com.github.jnet.Processor;

public class MySQLServer extends IoAcceptor {

    public MySQLServer(InetSocketAddress address, AbstractConnectionFactory factory) throws IOException {
        super(address, factory);
    }

    public static final void main(String args[]) throws IOException {
        AbstractConnectionFactory factory = new MySQLConnectionFactory();
        MySQLServer acceptor = new MySQLServer(new InetSocketAddress("127.0.0.1", 3307), factory);
        Processor[] processors = new Processor[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new Processor(i + "");
        }
        // 设置处理器
        acceptor.setProcessors(processors);
        new Thread(acceptor).start();
    }

}
