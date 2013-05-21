package com.github.mybridge.transport.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.github.mybridge.Launcher;
import com.github.mybridge.config.ServerConfiguration;

public class NettyLauncher implements Launcher {

    private ServerConfiguration config;

    public NettyLauncher() {}

    public NettyLauncher(ServerConfiguration config) {
        this.config = config;
    }

    @Override
    public void start() {
        this.init();
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors
                .newCachedThreadPool(), Executors.newCachedThreadPool()));
        // Set up the pipeline factory.
        bootstrap.setPipelineFactory(new NettyServerPipelineFactory());
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(config.getIp(), config.getPort()));

    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

}
