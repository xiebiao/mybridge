package org.github.mybridge.plugin.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


import org.github.mybridge.Configuration;
import org.github.mybridge.Launcher;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;

public class NettyLauncher implements Launcher {

	@Override
	public void start(Configuration config) throws Exception {
		InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
		// Configure the server.
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(config.getIp(), config.getPort()));

	}

}
