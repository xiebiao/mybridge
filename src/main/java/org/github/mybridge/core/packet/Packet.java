package org.github.mybridge.core.packet;

public abstract class Packet {

	public abstract byte[] getBytes();

	public abstract void putBytes(byte[] bytes);

	private static byte packetId = 0;

	public static synchronized void packetIdInc() {
		packetId++;
	}

	public static synchronized void setPacketId(byte id) {
		packetId = id;
	}

	public byte getPacketId() {
		return packetId;
	}
}
