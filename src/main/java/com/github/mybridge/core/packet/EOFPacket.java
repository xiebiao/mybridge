package com.github.mybridge.core.packet;

import com.github.mybridge.core.buffer.ByteBuffer;

/**
 * <pre>
 * From Server To Client, at the end of a series of Field Packets, and at the end of a series of Data Packets. With prepared statements, EOF Packet can also end parameter information, which we'll describe later.
 * 
 * VERSION 4.0
 *  Bytes                 Name
 *  -----                 ----
 *  1                     field_count, always = 0xfe
 *  
 *  VERSION 4.1
 *  Bytes                 Name
 *  -----                 ----
 *  1                     field_count, always = 0xfe
 *  2                     warning_count
 *  2                     Status Flags
 *  
 *  field_count:          The value is always 0xfe (decimal 254).
 *                        However ... recall (from the
 *                        section "Elements", above) that the value 254 can begin
 *                        a Length-Encoded-Binary value which contains an 8-byte
 *                        integer. So, to ensure that a packet is really an EOF
 *                        Packet: (a) check that first byte in packet = 0xfe, (b)
 *                        check that size of packet < 9.
 *  
 *  warning_count:        Number of warnings. Sent after all data has been sent
 *                        to the client.
 *  
 *  server_status:        Contains flags like SERVER_MORE_RESULTS_EXISTS
 * Alternative terms: EOF Packet is also known as "Last Data Packet" or "End Packet".
 * 
 * Relevant MySQL source code:
 * (server) protocol.cc send_eof()
 * Example of EOF Packet
 *                     Hexadecimal                ASCII
 *                     -----------                -----
 * field_count         fe                         .
 * warning_count       00 00                      ..
 * server_status       00 00                      ..
 * </pre>
 * 
 * @author xiebiao
 * 
 */
public class EOFPacket extends Packet {
	private byte type = (byte) 0xfe;
	private int warningCount = 0;
	private int statusFlags = 0;

	@Override
	public byte[] getBytes() {
		int len = 5;
		ByteBuffer buf = new ByteBuffer(len);
		buf.putByte(type);
		buf.putUInt16(warningCount);
		buf.putUInt16(statusFlags);
		return buf.getBytes();
	}

	@Override
	public void putBytes(byte[] bs) {
	}

}
