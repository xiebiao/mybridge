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
	static int sum = 0;

	public DataDecoder() {
		LOG.debug("DataDecoder init...");
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() < 5) {
			return null;
		} else {
			byte[] bytes = new byte[buffer.capacity() - 4];
			buffer.getBytes(4, bytes, 0, bytes.length);
			HeaderPacket header = new HeaderPacket();
			header.putBytes(bytes);
			header.setPacketId((byte)(header.getPacketId()+1));
			header.packetIdInc();
			buffer.skipBytes(4);
			sum++;
			return buffer;
		}

	}
}
