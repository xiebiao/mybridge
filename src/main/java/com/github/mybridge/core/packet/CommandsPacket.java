package com.github.mybridge.core.packet;

import com.github.mybridge.core.buffer.ByteBuffer;

/**
 * <pre>
 * From client to server whenever the client wants the server to do something.
 * 
 * Bytes                        Name
 * -----                        ----
 * 1                            command
 * n                            arg
 * 
 * The command byte is stored in the thd structure for the MySQL worker threads and is shown in the Command column for SHOW PROCESSLIST. 
 * An inactive thread gets 0x00 (Sleep).   The dedicated thread to execute INSERT DELAYED gets 0x10.
 * 
 * The replication requests (0x12 .. 0x15) cannot be send from regular clients, only from another server or from the mysqlbinlog program.
 * 
 * Relevant MySQL source code: 
 * sql-common/client.c cli_advanced_command(), mysql_send_query().
 * libmysql/libmysql.c mysql_real_query(), simple_command(), net_field_length().
 * 
 * Example Command Packet 
 * 								Hexadecimal    ASCII 
 *  								----------- 				----- 
 * command             02       					 . 
 * arg                 			74 65 73 74      test 
 * 
 * In the example, the value 02 in the command field stands for COM_INIT_DB. This is the packet that the client puts together for "use test;".
 * </pre>
 * 
 * @author xiaog
 * 
 */
public class CommandsPacket extends Packet {
	private byte type;// 占一个字节,表示客户端请求类型,如0x03 mysql_real_query
	private byte[] value;

	@Override
	public byte[] getBytes() {
		return value;
	}

	@Override
	public void putBytes(byte[] bs) {
		ByteBuffer buf = new ByteBuffer(bs);
		type = buf.readByte();
		value = buf.readRemainBytes();
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getType() {
		return type;
	}

	public byte[] getValue() {
		return value;
	}

}
