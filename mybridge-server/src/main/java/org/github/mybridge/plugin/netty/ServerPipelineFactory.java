package org.github.mybridge.plugin.netty;


import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;

public class ServerPipelineFactory implements ChannelPipelineFactory {
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		//pipeline.addLast("framer", new DelimiterBasedFrameDecoder());
		pipeline.addLast("decoder", new DataDecoder());
		pipeline.addLast("encoder", new DataEncoder());	
		pipeline.addLast("handler", new ServerHandler());
		return pipeline;
	}
}
