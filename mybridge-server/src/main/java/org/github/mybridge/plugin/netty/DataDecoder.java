package org.github.mybridge.plugin.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class DataDecoder extends FrameDecoder {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	static int READ_HEADER = 0;//
	static int READ_BODY = 1;//
	int currentState = READ_HEADER;

	public DataDecoder() {
		LOG.debug("DataDecoder init...");
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		LOG.debug("READ_HEADER:readable");
		if (currentState == READ_HEADER) {
			currentState = READ_BODY;
			if (buffer.readable()) {
				LOG.debug("READ_HEADER:readable");
			}
		} else {
			currentState = READ_HEADER;
			if (buffer.readable()) {
				LOG.debug("READ_BODY:readable");
			}
		}
		return null;
	}
}
