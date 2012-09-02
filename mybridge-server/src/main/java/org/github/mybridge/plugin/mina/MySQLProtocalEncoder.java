package org.github.mybridge.plugin.mina;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.github.mybridge.core.packet.PacketHeader;
import org.github.mybridge.core.packet.PacketNum;


public class MySQLProtocalEncoder extends ProtocolEncoderAdapter {

	private IoBuffer buffer;

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		byte[] temp = (byte[]) message;
		buffer = IoBuffer.allocate(temp.length + 4);
		PacketHeader header = new PacketHeader();
		header.packetLen = temp.length;
		header.packetNum = PacketNum.num;
		PacketNum.add();
		buffer.put(header.getBytes());
		buffer.put(temp);
		buffer.flip();

		out.write(buffer);
		out.flush();
	}

}