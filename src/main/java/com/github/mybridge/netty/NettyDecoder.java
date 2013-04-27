package com.github.mybridge.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.github.mybridge.core.packet.PacketHeader;
import com.mysql.jdbc.StringUtils;

/**
 * 解码
 * 
 * @author xiebiao
 * 
 */
public class NettyDecoder extends FrameDecoder {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(NettyDecoder.class);
	static int READ_HEADER = 0;//
	static int READ_BODY = 1;//
	int currentState = READ_HEADER;

	public NettyDecoder() {
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {

		if (buffer.readableBytes() < 5) {
			return null;
		} else {
			//byte[] header = new byte[4];
			// buffer.getBytes(0, header);
			// PacketHeader headerPacket = new PacketHeader();
			// headerPacket.putBytes(header);
			buffer.skipBytes(4);
			//logger.debug(StringUtils.dumpAsHex(header, header.length));
			return buffer;
		}
	}
}
