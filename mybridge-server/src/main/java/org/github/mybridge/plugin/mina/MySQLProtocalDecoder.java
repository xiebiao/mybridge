package org.github.mybridge.plugin.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.github.mybridge.core.packet.HeaderPacket;
import org.github.mybridge.core.packet.Packet;

public class MySQLProtocalDecoder extends ProtocolDecoderAdapter {
	// private final org.slf4j.Logger LOG =
	// org.slf4j.LoggerFactory.getLogger(this
	// .getClass());
	static int READ_HEADER = 0;//
	static int READ_BODY = 1;//
	int currentState = READ_HEADER;

	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		if (currentState == READ_HEADER) {
			currentState = READ_BODY;
			byte[] bytes = new byte[in.limit()];
			in.get(bytes, 0, in.limit());
			HeaderPacket header = new HeaderPacket();
			header.putBytes(bytes);
			Packet.setPacketId((byte) (header.getPacketId() + 1));
			in.flip();
			in.position(4);
			in.limit(header.getPacketLen() + 4);
		} else {			
			currentState = READ_HEADER;
			byte[] msg = new byte[in.limit() - 4];
			in.get(msg);
			out.write(msg);
		}

	}

}
