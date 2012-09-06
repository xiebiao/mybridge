package org.github.mybridge.plugin.netty;


import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class ServerPipelineFactory implements ChannelPipelineFactory {
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", new DataDecoder());
		pipeline.addLast("encoder", new DataEncoder());
		pipeline.addLast("handler", new ServerHandler());
		return pipeline;
	}
}
