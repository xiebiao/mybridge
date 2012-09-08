package org.github.mybridge.core.packet;

public class PacketNum {
	private static byte num = 0;

	public static synchronized void add() {
		num++;
	}

	public static synchronized void set(byte b) {
		num = b;
	}

	public static byte get() {
		return num;
	}
}
