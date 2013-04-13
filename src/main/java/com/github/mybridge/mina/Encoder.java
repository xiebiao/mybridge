package com.github.mybridge.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.github.mybridge.core.packet.PacketHeader;
import com.github.mybridge.core.packet.Packet;

public class Encoder extends ProtocolEncoderAdapter {
	// private final org.slf4j.Logger LOG =
	// org.slf4j.LoggerFactory.getLogger(this
	// .getClass());
	private IoBuffer buffer;

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		byte[] msg = (byte[]) message;
		buffer = IoBuffer.allocate(msg.length + 4);
		PacketHeader header = new PacketHeader();
		header.setPacketLen(msg.length);
		header.setPacketId(header.getPacketId());
		header.packetIdInc();
		buffer.put(header.getBytes());
		buffer.put(msg);
		buffer.flip();
		out.write(buffer);
		out.flush();
	}

}