package com.github.mybridge.core.packet;

public abstract class Packet {

	public abstract byte[] getBytes();

	public abstract void putBytes(byte[] bytes);

	private byte packetId = 0;

	public synchronized void packetIdInc() {
		packetId++;
	}

	public synchronized void setPacketId(byte id) {
		packetId = id;
	}

	public byte getPacketId() {
		return packetId;
	}
}
