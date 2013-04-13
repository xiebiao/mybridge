package com.github.mybridge.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.github.mybridge.core.packet.HeaderPacket;
import com.github.mybridge.core.packet.Packet;
import com.mysql.jdbc.StringUtils;

public class Encoder extends OneToOneEncoder {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(Encoder.class);

	public Encoder() {

	}

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {

		byte[] bytes = (byte[]) msg;
		logger.debug(StringUtils.dumpAsHex(bytes, bytes.length));
		HeaderPacket header = new HeaderPacket();
		header.setPacketLen(bytes.length);
		Packet.setPacketId(header.getPacketId());
		ChannelBuffer buffer = ChannelBuffers.buffer(bytes.length + 4);
		buffer.writeBytes(header.getBytes());
		buffer.writeBytes(bytes);
		return buffer;
	}
}
