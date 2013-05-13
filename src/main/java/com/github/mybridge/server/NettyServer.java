package com.github.mybridge.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.github.jnet.Configuration;
import com.github.mybridge.Lifecycle;

public class NettyServer implements Lifecycle {

    private Configuration   config;
    private ServerBootstrap bootstrap;

    public NettyServer() {}

    public NettyServer(Configuration config) {
        this.config = config;
    }

    @Override
    public void start() {
        // Configure the server.
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors
                .newCachedThreadPool()));
        // Set up the pipeline factory.
        bootstrap.setPipelineFactory(new NettyServerPipelineFactory());
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(config.getIp(), config.getPort()));

    }

    @Override
    public void stop() {
        bootstrap.shutdown();
    }

}
