package org.github.mybridge.plugin.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.github.mybridge.core.packet.HeaderPacket;
import org.github.mybridge.core.packet.PacketNum;
import org.github.mybridge.utils.StringUtils;

public class MySQLProtocalEncoder extends ProtocolEncoderAdapter {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	private IoBuffer buffer;
	public static int num = 0;

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		byte[] msg = (byte[]) message;
		buffer = IoBuffer.allocate(msg.length + 4);
		HeaderPacket header = new HeaderPacket();
		header.setPacketLen(msg.length);
		header.setPacketId(header.getPacketId());
		header.packetIdInc();
		if (num <= 2) {
			LOG.debug(num + ": packetId:" + header.getPacketId());
			LOG.debug(num + ":" + StringUtils.printHex(header.getBytes()));
			LOG.debug(num + ":" + StringUtils.printHex(msg));
		}
		buffer.put(header.getBytes());
		buffer.put(msg);
		buffer.flip();
		out.write(buffer);
		out.flush();
		num++;
	}

}