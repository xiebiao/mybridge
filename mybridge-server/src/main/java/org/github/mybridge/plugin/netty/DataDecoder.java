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
		if (buffer.readableBytes() < 4) {
			return null;
		}
		LOG.debug("READ_HEADER:" + sum);
//		currentState = READ_BODY;
//		byte[] bytes = new byte[buffer.capacity() - 4];
//		buffer.getBytes(4, bytes, 0, bytes.length);
//		// 这里可以使用netty POJO编码
//		HeaderPacket header = new HeaderPacket();
//		header.putBytes(bytes);
//		LOG.debug(StringUtils.printHex(bytes));
//		PacketNum.num = (byte) (header.packetNum + 1);
		buffer.skipBytes(4);
		sum++;
		return buffer;

	}
}
