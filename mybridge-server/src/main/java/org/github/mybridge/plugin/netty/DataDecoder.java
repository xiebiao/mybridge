package org.github.mybridge.plugin.netty;

import org.github.mybridge.core.packet.HeaderPacket;
import org.github.mybridge.core.packet.PacketNum;
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
			byte[] by = new byte[buffer.capacity()];
			buffer.getBytes(0, by, 0, by.length);			
			HeaderPacket header = new HeaderPacket();
			header.putBytes(by);
			PacketNum.num = (byte) (header.packetNum + 1);
			currentState = READ_BODY;
		} else {
			currentState = READ_HEADER;
			if (buffer.readable()) {
				LOG.debug("READ_BODY:readable");
			}
		}
		return null;
	}
}
