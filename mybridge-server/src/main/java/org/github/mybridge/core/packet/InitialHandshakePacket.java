package org.github.mybridge.core.packet;

import org.github.mybridge.core.buffer.ByteBuffer;

public class InitialHandshakePacket extends Packet {

	private byte protocol_version = 0x0a;
	private String serverVersion = "5.5.22 MySQL Community Server \0";// 这里要用'\0'结束
	private long thread_id = Thread.currentThread().getId();
	private byte[] scramble_buff = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 };
	private byte filler = 0x00;
	private int server_capabilities = 63487;
	private byte server_language = 8;
	private int serverStatus = 0;
	private byte[] filler2 = new byte[13];//
	private byte[] lastScrambleBuff = new byte[13];

	/**
	 * when client connect server,ther server return init packet to client,
	 * called greet
	 */
	private byte[] initPacket() {
		/**
		 * <pre>
		 *  Bytes                        Name<
		 *  -----                        ----
		 *  1                            protocol_version
		 *  n (Null-Terminated String)   server_version
		 *  4                            thread_id
		 *  8                            scramble_buff
		 *  1                            (filler) always 0x00
		 *  2                            server_capabilities
		 *  1                            server_language
		 *  2                            server_status
		 *  13                           (filler) always 0x00 ...
		 *  13                           rest of scramble_buff (4.1)
		 * </pre>
		 */
		int length = 45 + serverVersion.length();
		ByteBuffer buffer = new ByteBuffer(length);
		buffer.putByte(protocol_version);
		buffer.putNullString(serverVersion);
		buffer.putUInt32(thread_id);
		buffer.putBytes(scramble_buff);
		buffer.putByte(filler);
		buffer.putUInt16(server_capabilities);
		buffer.putByte(server_language);
		buffer.putUInt16(serverStatus);
		buffer.putBytes(filler2);
		buffer.putBytes(lastScrambleBuff);
		return buffer.getBytes();
	}

	@Override
	public byte[] getBytes() {
		return initPacket();
	}

	@Override
	public void putBytes(byte[] bytes) {

	}
}
