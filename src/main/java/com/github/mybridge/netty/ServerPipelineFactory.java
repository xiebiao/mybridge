package com.github.mybridge.netty;


import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.logging.LoggingHandler;

public class ServerPipelineFactory implements ChannelPipelineFactory {
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("log", new LoggingHandler());
		pipeline.addLast("decoder", new Decoder());
		pipeline.addLast("encoder", new Encoder());	
		pipeline.addLast("handler", new ServerHandler());
		return pipeline;
	}
}
