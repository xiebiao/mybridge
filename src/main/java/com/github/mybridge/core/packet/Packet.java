package com.github.mybridge.core.packet;

public abstract class Packet {

	public abstract byte[] getBytes();

	public abstract void putBytes(byte[] bytes);

	private byte packetNumber = 0;

	public synchronized void packetNumberInc() {
		packetNumber++;
	}

	public synchronized void setPacketNumber(byte id) {
		packetNumber = id;
	}

	public byte getPacketNumber() {
		return packetNumber;
	}
}
