package org.github.mybridge.core.packet;

import org.github.mybridge.core.buffer.ByteBuffer;

/**
 * <pre>
 * From server to client, or from client to server (if the client has a prepared statement, the "result set" packet format is used for transferring parameter descriptors and parameter data).
 * 
 * Recall that in the description of Row Data we said that: "If a column is defined as non-character, the server converts the value into a character before sending it." That doesn't have to be true. If it isn't true, it's a Row Data Packet: Binary.
 * 
 * Bytes                   Name
 *  -----                   ----
 *  1                       0 (packet header)
 *  (col_count+7+2)/8       Null Bit Map with first two bits = 01
 *  n                       column values
 *  
 *  Null Bit Map: The first 2 bits are reserved. Since
 *                there is always one bit on and one bit off, this can't be
 *                confused with the first byte of an Error Packet (255), the
 *                first byte of a Last Data Packet (254), or the first byte of
 *                an OK Packet (0).  
 *                NOTE: MySQL 5.x these 2 bits are always 0.
 *  
 *  (column value): The column order and organization are the same as for
 *                  conventional Row Data Packets. The difference is that
 *                  each column value is sent just as it is stored. It's now up
 *                  to the client to convert numbers to strings if that's desirable.
 *                  For a description of column storage, see "Physical Attributes Of
 *                  Columns" elsewhere in this document.
 * Only non-NULL parameters are passed.
 * 
 * Because no conversion takes place, fixed-length data items are as described in the "Physical Attributes of Columns" section: one byte for TINYINT, two bytes for FLOAT, four bytes for FLOAT, etc. Strings will appear as packed-string-length plus string value. DATETIME, DATE and TIME will be as follows:
 * 
 * Type             Size        Comment
 *  ----             ----        -------
 *  date             1 + 0-11    Length + 2 byte year, 1 byte MMDDHHMMSS,
 *                               4 byte billionth of a second
 *  datetime         1 + 0-11    Length + 2 byte year, 1 byte MMDDHHMMSS,
 *                               4 byte billionth of a second
 *  time             1 + 0-11    Length + sign (0 = pos, 1= neg), 4 byte days,
 *                               1 byte HHMMDD, 4 byte billionth of a second
 * 
 *  If the sub-second part is 0, it isn't sent.
 *  If the time-part is 00:00:00 too, it isnt' sent either.
 *  If all fields are 0, nothing is sent, but the length byte.
 * Alternative Terms: Row Data Packet: Binary is also called "Binary result set packet".
 * 
 * Except for the different way of signalling NULLs, the server/client parameter interaction here proceeds the say way that the server sends result set data to the client. Since the data is not sent as a string, the length and meaning depend on the data type. The client must make appropriate conversions given its knowledge of the data type.
 * </pre>
 * 
 * @author xiebiao
 * 
 */
public class ResultSetBinaryPacket extends BasePacket {
	public long fieldCount;

	@Override
	public byte[] getBytes() {
		int len = ByteBuffer.getLCBLen(fieldCount);
		ByteBuffer buf = new ByteBuffer(len);
		buf.putLCB(fieldCount);
		return buf.getBytes();
	}

	@Override
	public void putBytes(byte[] bs) {
	}

}
