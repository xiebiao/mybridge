package org.github.mybridge.plugin.netty;

import org.github.mybridge.core.packet.HeaderPacket;
import org.github.mybridge.core.packet.PacketNum;
import org.github.mybridge.utils.StringUtils;
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
		if (currentState == READ_HEADER) {
			LOG.debug("READ_HEADER:readable");
			currentState = READ_BODY;
			byte[] by = new byte[buffer.capacity()];
			buffer.getBytes(0, by, 0, by.length);
			//这里可以使用netty POJO编码
			HeaderPacket header = new HeaderPacket();
			header.putBytes(by);
			LOG.debug(StringUtils.printHex(by));
			PacketNum.num = (byte) (header.packetNum + 1);
			//channel.write(by);
			return header;
	} else {
			currentState = READ_HEADER;
			if (buffer.readable()) {
				LOG.debug("READ_BODY:readable");
			}
		}
		return null;
	}
}
