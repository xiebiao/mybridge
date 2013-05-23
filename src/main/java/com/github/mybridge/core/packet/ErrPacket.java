package com.github.mybridge.core.packet;

import com.github.mybridge.core.buffer.ByteBuffer;

/**
 * <pre>
 * From server to client in response to command, if error.
 * 
 * VERSION 4.0
 * Bytes                       Name
 * -----                       ----
 * 1                           field_count, always = 0xff
 * 2                           errno (little endian)
 * n                           message
 * 
 * VERSION 4.1
 * Bytes                       Name
 * -----                       ----
 * 1                           field_count, always = 0xff
 * 2                           errno
 * 1                           (sqlstate marker), always '#'
 * 5                           sqlstate (5 characters)
 * n                           message
 * 
 * field_count:       Always 0xff (255 decimal).
 * 
 * errno:             The possible values are listed in the manual, and in
 *                   the MySQL source code file /include/mysqld_error.h.
 * 
 * sqlstate marker:   This is always '#'. It is necessary for distinguishing
 *                   version-4.1 messages.
 * 
 * sqlstate:          The server translates errno values to sqlstate values
 *                   with a function named mysql_errno_to_sqlstate(). The
 *                   possible values are listed in the manual, and in the
 *                   MySQL source code file /include/sql_state.h.
 * 
 * message:           The error message is a string which ends at the end of
 *                   the packet, that is, its length can be determined from
 *                   the packet header. The MySQL client (in the my_net_read()
 *                   function) always adds '\0' to a packet, so the message
 *                   may appear to be a Null-Terminated String.
 *                   Expect the message to be between 0 and 512 bytes long.
 * Alternative terms: field_count is also known as "Status code" or "Error Packet marker". errno is also known as "Error Number" or "Error Code".
 * 
 * Relevant files in MySQL source: (client) client.c net_safe_read() (server) sql/protocol.cc send_error()
 * 
 * Example of Error Packet
 *                     Hexadecimal                ASCII
 *                     -----------                -----
 * field_count         ff                         .
 * errno               1b 04                      ..
 * (sqlstate marker)   23                         #
 * sqlstate            34 32 53 30 32             42S02
 * message             55 63 6b 6e 6f 77 6e 20    Unknown
 *                     74 61 62 6c 6c 65 20 27    table '
 *                     71 27                      q'
 * Note that some error messages past MySQL 4.1 are still returned without SQLState. For example, error 1043 'Bad handshake'.
 * </pre>
 * 
 * @author xiebiao
 * 
 */
public class ErrPacket extends AbstractPacket implements Packet{
	private byte type = (byte) 0xff;
	private int errno = 0;
	private byte sqlStateMark = 0x23;
	private String sqlstate = "12345";
	private String message = "";

	public ErrPacket() {
	}

	public ErrPacket(int errno, String message) {
		super();
		this.errno = errno;
		this.message = message;
	}

	public ErrPacket(int errorCode, String sqlState, String message) {
		this.errno = errorCode;
		this.sqlstate = sqlState;
		this.message = message;
	}

	@Override
	public byte[] getBytes() {
		int len = 9 + message.getBytes().length;
		ByteBuffer buf = new ByteBuffer(len);
		buf.putByte(type);
		buf.putUInt16(errno);
		buf.putByte(sqlStateMark);
		buf.putBytes(sqlstate.getBytes());
		buf.putRemainString(message);
		return buf.getBytes();
	}

	@Override
	public void putBytes(byte[] bs) {
	}

}
