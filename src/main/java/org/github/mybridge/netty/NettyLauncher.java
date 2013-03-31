package org.github.mybridge.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.github.mybridge.Configuration;
import org.github.mybridge.Launcher;
import org.github.mybridge.exception.ConfigurationException;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyLauncher implements Launcher {
	private Configuration parameter;

	public NettyLauncher() {
	}

	public NettyLauncher(Configuration parameter) {
		this.parameter = parameter;
	}

	@Override
	public void start() {
		try {
			this.init();
			// Configure the server.
			ServerBootstrap bootstrap = new ServerBootstrap(
					new NioServerSocketChannelFactory(
							Executors.newCachedThreadPool(),
							Executors.newCachedThreadPool()));
			// Set up the pipeline factory.
			bootstrap.setPipelineFactory(new ServerPipelineFactory());
			// Bind and start to accept incoming connections.
			bootstrap.bind(new InetSocketAddress(parameter.getIp(), parameter
					.getPort()));
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void init() throws ConfigurationException {

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
