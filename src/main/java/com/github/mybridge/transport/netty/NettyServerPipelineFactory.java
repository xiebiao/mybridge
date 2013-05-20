package com.github.mybridge.transport.netty;


import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.logging.LoggingHandler;

public class NettyServerPipelineFactory implements ChannelPipelineFactory {
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("log", new LoggingHandler());
		pipeline.addLast("decoder", new NettyDecoder());
		pipeline.addLast("encoder", new NettyEncoder());	
		pipeline.addLast("handler", new NettyServerHandler());
		return pipeline;
	}
}
