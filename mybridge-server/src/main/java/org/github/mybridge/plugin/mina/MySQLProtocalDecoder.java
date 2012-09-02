package org.github.mybridge.plugin.mina;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.github.mybridge.core.packet.HeaderPacket;
import org.github.mybridge.core.packet.PacketNum;


public class MySQLProtocalDecoder extends ProtocolDecoderAdapter {
	static int READ_HEADER = 0;//
	static int READ_BODY = 1;//
	int currentState = READ_HEADER;

	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		if (currentState == READ_HEADER) {
			byte[] by = new byte[in.limit()];
			in.get(by, 0, in.limit());
			HeaderPacket header = new HeaderPacket();
			header.putBytes(by);
			PacketNum.num = (byte) (header.packetNum + 1);
			in.flip();
			in.position(4);
			in.limit(header.packetLen + 4);
			currentState = READ_BODY;
		} else {
			currentState = READ_HEADER;
			byte[] temp = new byte[in.limit() - 4];
			in.get(temp);
			out.write(temp);

		}

	}

}
