package org.github.mybridge.plugin.netty;

import org.github.mybridge.core.packet.HeaderPacket;
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
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() < 5) {
			return null;
		} else {
			byte[] header = new byte[4];
			buffer.getBytes(0, header);
			HeaderPacket headerPacket = new HeaderPacket();
			headerPacket.putBytes(header);
			buffer.skipBytes(4);
			//byte[] body = new byte[buffer.readableBytes()];
			//buffer.readBytes(body);
			if (sum < 3) {
				LOG.debug("READ_HEADER bytes.length:" + header.length
						+ " packetId:" + headerPacket.getPacketId() + " bytes:"
						+ StringUtils.printHex(header));
			}
			// header.packetIdInc();

			sum++;
			return buffer;
		}
		// if (buffer.readableBytes() < 5) {
		// return null;
		// }
		// if (currentState == READ_HEADER) {
		// currentState = READ_BODY;
		// byte[] bytes = new byte[buffer.readableBytes()];
		// buffer.getBytes(0, bytes, 0, bytes.length);
		// HeaderPacket header = new HeaderPacket();
		// header.putBytes(bytes);
		// LOG.debug("READ_HEADER bytes.length:"+bytes.length+" packetId:" +
		// header.getPacketId());
		// PacketNum.set((byte) (header.getPacketId() + 1));
		// buffer.skipBytes(4);
		// return null;
		// } else {
		// currentState = READ_HEADER;
		// //debug
		// byte[] msg = new byte[buffer.readableBytes()];
		// buffer.getBytes(0, msg, 0, msg.length);
		// LOG.debug("READ_BODY bytes.length:"+msg.length+" packetId:" +
		// StringUtils.printHex(msg));
		// return buffer;
		// }

	}
}
