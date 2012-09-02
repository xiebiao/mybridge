package org.github.mybridge.core.packet;

public class PacketNum {
	public static byte num = 0;
	
	public static synchronized void add(){
		num++;
	}
}
