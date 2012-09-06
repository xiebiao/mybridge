package org.github.mybridge.plugin.netty;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class DataEncoder extends OneToOneEncoder {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	public DataEncoder() {
		LOG.debug("DataEncoder init...");
	}

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		LOG.debug("DataEncoder encode");
		LOG.debug(msg.getClass().getName());
		PacketBuffer packetBuf = (PacketBuffer) msg;
		return packetBuf.buffer;
	}
}
