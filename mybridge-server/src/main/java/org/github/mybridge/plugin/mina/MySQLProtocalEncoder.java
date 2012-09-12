package org.github.mybridge.plugin.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.github.mybridge.core.packet.HeaderPacket;
import org.github.mybridge.core.packet.Packet;

public class MySQLProtocalEncoder extends ProtocolEncoderAdapter {
	// private final org.slf4j.Logger LOG =
	// org.slf4j.LoggerFactory.getLogger(this
	// .getClass());
	private IoBuffer buffer;

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		byte[] msg = (byte[]) message;
		buffer = IoBuffer.allocate(msg.length + 4);
		HeaderPacket header = new HeaderPacket();
		header.setPacketLen(msg.length);
		Packet.setPacketId(header.getPacketId());
		Packet.packetIdInc();
		buffer.put(header.getBytes());
		buffer.put(msg);
		buffer.flip();
		out.write(buffer);
		out.flush();
	}

}