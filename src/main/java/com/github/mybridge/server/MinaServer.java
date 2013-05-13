package com.github.mybridge.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.github.jnet.Configuration;
import com.github.mybridge.Lifecycle;

public class MinaServer implements Lifecycle {

    private final org.slf4j.Logger logger   = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private Configuration          config;
    private IoAcceptor             acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);

    public MinaServer(Configuration config) {
        this.config = config;
    }

    public void start() {

        // DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        // LoggingFilter loggingFilter = new LoggingFilter();
        // chain.addLast("logging", loggingFilter);

        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaCodecFactory()));
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        acceptor.setHandler(new MinaMySQLProtocolImpl());
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        InetSocketAddress address = new InetSocketAddress(config.getIp(), config.getPort());
        try {
            acceptor.bind(address);
            logger.debug("MinaLauncher started");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void stop() {
        acceptor.unbind();
    }

}
