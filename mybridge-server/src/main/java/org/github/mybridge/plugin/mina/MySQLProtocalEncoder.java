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

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		byte[] temp = (byte[]) message;
		buffer = IoBuffer.allocate(temp.length + 4);
		HeaderPacket header = new HeaderPacket();
		header.packetLen = temp.length;
		header.packetNum = PacketNum.num;
		PacketNum.add();
		LOG.debug(StringUtils.printHex(header.getBytes()));
		LOG.debug(StringUtils.printHex(temp));
		buffer.put(header.getBytes());
		buffer.put(temp);
		buffer.flip();
		out.write(buffer);
		out.flush();
	}

}