package org.github.mybridge.core.packet;

public abstract class BasePacket {

	public abstract byte[] getBytes();

	public abstract void putBytes(byte[] bytes);

}
