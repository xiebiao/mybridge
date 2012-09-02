package mybridge2.startup;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import mybridge2.netty.ServerHandler;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;

public class MybridgeServer {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
		// Configure the server.
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels
						.pipeline(new ServerHandler());
				//pipeline.addLast("decoder", new DataDecoder());
				//pipeline.addLast("encoder", new DataEncoder());
				pipeline.addLast("log", new LoggingHandler());
				return pipeline;
			}
		});
		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress("127.0.0.1", 3307));
	}

}
