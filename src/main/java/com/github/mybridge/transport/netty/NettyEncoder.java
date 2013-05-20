package com.github.mybridge.transport.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.github.mybridge.core.packet.PacketHeader;

/**
 * 编码
 * 
 * @author xiebiao
 * 
 */
public class NettyEncoder extends OneToOneEncoder {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(NettyEncoder.class);

	public NettyEncoder() {

	}

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		byte[] body = (byte[]) msg;
		// logger.debug(StringUtils.dumpAsHex(body, body.length));
		PacketHeader header = new PacketHeader();
		header.setPacketLen(body.length);
		header.setPacketNumber(header.getPacketNumber());
		ChannelBuffer buffer = ChannelBuffers.buffer(body.length + 4);
		buffer.writeBytes(header.getBytes());
		buffer.writeBytes(body);
		return buffer;
	}
}
