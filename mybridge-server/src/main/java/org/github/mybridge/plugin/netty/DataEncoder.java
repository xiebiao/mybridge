package org.github.mybridge.plugin.netty;

import org.github.mybridge.core.packet.HeaderPacket;
import org.github.mybridge.core.packet.PacketNum;
import org.github.mybridge.utils.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
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
		byte[] body = (byte[]) msg;
		HeaderPacket header = new HeaderPacket();
		header.packetLen = body.length;
		header.packetNum = PacketNum.num;
		ChannelBuffer buffer = ChannelBuffers.buffer(body.length + 4);
		//debug
		LOG.debug(StringUtils.printHex(header.getBytes()));
		LOG.debug(StringUtils.printHex(body));
		buffer.writeBytes(header.getBytes());
		if(buffer.writable()){
			buffer.writeBytes(body);
		}	
		return buffer;
	}
}
