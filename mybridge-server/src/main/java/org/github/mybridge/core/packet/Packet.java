package org.github.mybridge.core.packet;

public abstract class Packet {

	public abstract byte[] getBytes();

	public abstract void putBytes(byte[] bytes);

}
