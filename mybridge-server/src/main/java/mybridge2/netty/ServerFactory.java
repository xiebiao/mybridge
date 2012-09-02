package mybridge2.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author zKF36895
 * @version [版本号, 2011-5-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServerFactory implements ChannelPipelineFactory {
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", new DataDecoder());
		pipeline.addLast("encoder", new DataEncoder());
		pipeline.addLast("handler", new ServerHandler());
		return pipeline;
	}
}
