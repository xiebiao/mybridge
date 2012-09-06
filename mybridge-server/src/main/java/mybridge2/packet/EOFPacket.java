package mybridge2.packet;

import java.io.UnsupportedEncodingException;

import org.github.mybridge.plugin.netty.PacketBuffer;


public class EOFPacket extends Packet {
	private static final byte PACKET_TYPE_EOF = (byte) 0xfe;

	private int serverStatus = 2;

	private int warningCount = 0;

	public int getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(int serverStatus) {
		this.serverStatus = serverStatus;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}

	public static byte getPacketTypeEof() {
		return PACKET_TYPE_EOF;
	}

	public void write2Buffer(PacketBuffer buffer)
			throws UnsupportedEncodingException {
		super.write2Buffer(buffer);
		buffer.writeByte(PACKET_TYPE_EOF);
		buffer.writeMySQLInt(this.warningCount);
		buffer.writeMySQLInt(this.serverStatus);
		super.afterPacketWritten(buffer);

	}

}