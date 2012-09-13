package org.github.mybridge.plugin.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.github.mybridge.Launcher;
import org.github.mybridge.AbstractLauncher;
import org.github.mybridge.exception.ConfigurationException;
import org.github.mybridge.utils.Configuration;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;

public class NettyLauncher extends AbstractLauncher implements Launcher {
	private Configuration config;

	public NettyLauncher(Configuration config) {
		this.config = config;
	}

	@Override
	public void start() {
		try {
			this.init();
			InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
			// Configure the server.
			ServerBootstrap bootstrap = new ServerBootstrap(
					new NioServerSocketChannelFactory(
							Executors.newCachedThreadPool(),
							Executors.newCachedThreadPool()));
			// Set up the pipeline factory.
			bootstrap.setPipelineFactory(new ServerPipelineFactory());
			// Bind and start to accept incoming connections.
			bootstrap.bind(new InetSocketAddress(config.getIp(), config
					.getPort()));
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void init() throws ConfigurationException {
		super.init();
	}

}
