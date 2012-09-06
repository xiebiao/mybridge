package org.github.mybridge.core.packet;

/**
 * <pre>
 * Bytes                 Name
 * -----                 ----
 * 3                     Packet Length
 * 1                     Packet Number
 * 
 * Packet Length: The length, in bytes, of the packet
 *                that follows the Packet Header. There
 *                may be some special values in the most
 *                significant byte. Since 2**24 = 16MB,
 *                the maximum packet length is 16MB.
 *        
 * Packet Number: A serial number which can be used to
 *                ensure that all packets are present
 *                and in order. The first packet of a
 *                client query will
 *                have Packet Number = 0. Thus, when a
 *                new SQL statement starts, the packet
 *                number is re-initialized.
 * The Packet Header will not be shown in the descriptions of packets that follow this section. Think of it as always there. 
 * But logically, it "precedes the packet" rather than "is included in the packet".
 * 
 * Indeed,if the packet length is equal or greater than (2**24 -1) Bytes, this packet must be split into two or more packets.
 * 
 * Alternative terms: Packet Length is also called "packetsize". Packet Number is also called "Packet no".
 * </pre>
 */

public class HeaderPacket extends Packet {
	public final static int size = 4;
	public int packetLen;
	public byte packetNum;

	@Override
	public byte[] getBytes() {
		byte[] temp = new byte[size];
		temp[0] = (byte) (packetLen & 0xff);
		temp[1] = (byte) ((packetLen >> 8) & 0xff);
		temp[2] = (byte) ((packetLen >> 16) & 0xff);
		temp[3] = packetNum;
		return temp;
	}

	@Override
	public void putBytes(byte[] bs) {
		packetLen = (bs[0] & 0xff) | ((bs[1] & 0xff) << 8)
				| ((bs[2] & 0xff) << 16);
		packetNum = bs[3];
	}

}
