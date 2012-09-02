package org.github.mybridge.core.packet;

import org.github.mybridge.core.buffer.ByteBuffer;

/**
 * <pre>
 * From Server To Client, part of Result Set Packets. One for each column in the result set. Thus, if the value of field_columns in the Result Set Header Packet is 3, then the Field Packet occurs 3 times.
 * 
 * VERSION 4.0
 * Bytes                      Name
 * -----                      ----
 * n (Length Coded String)    table
 * n (Length Coded String)    name
 * 4 (Length Coded Binary)    length
 * 2 (Length Coded Binary)    type
 * 2 (Length Coded Binary)    flags
 * 1                          decimals
 * n (Length Coded Binary)    default
 * 
 * VERSION 4.1
 * Bytes                      Name
 * -----                      ----
 * n (Length Coded String)    catalog
 * n (Length Coded String)    db
 * n (Length Coded String)    table
 * n (Length Coded String)    org_table
 * n (Length Coded String)    name
 * n (Length Coded String)    org_name
 * 1                          (filler)
 * 2                          charsetnr
 * 4                          length
 * 1                          type
 * 2                          flags
 * 1                          decimals
 * 2                          (filler), always 0x00
 * n (Length Coded Binary)    default
 * In practice, since identifiers are almost always 250 bytes or shorter, the Length Coded Strings look like: (1 byte for length of data) (data)
 * 
 * catalog:                 Catalog. For 4.1, 5.0 and 5.1 the value is "def".
 * db:                      Database identifier, also known as schema name.
 * table:                   Table identifier, after AS clause (if any).
 * org_table:               Original table identifier, before AS clause (if any).
 * name:                    Column identifier, after AS clause (if any).
 * org_name:                Column identifier, before AS clause (if any).
 * charsetnr:               Character set number.
 * length:                  Length of column, according to the definition.
 *                          Also known as "display length". The value given
 *                          here may be larger than the actual length, for
 *                          example an instance of a VARCHAR(2) column may
 *                          have only 1 character in it.
 * type:                    The code for the column's data type. Also known as
 *                          "enum_field_type". The possible values at time of
 *                          writing (taken from  include/mysql_com.h), in hexadecimal:
 *                          0x00   FIELD_TYPE_DECIMAL
 *                          0x01   FIELD_TYPE_TINY
 *                          0x02   FIELD_TYPE_SHORT
 *                          0x03   FIELD_TYPE_LONG
 *                          0x04   FIELD_TYPE_FLOAT
 *                          0x05   FIELD_TYPE_DOUBLE
 *                          0x06   FIELD_TYPE_NULL
 *                          0x07   FIELD_TYPE_TIMESTAMP
 *                          0x08   FIELD_TYPE_LONGLONG
 *                          0x09   FIELD_TYPE_INT24
 *                          0x0a   FIELD_TYPE_DATE
 *                          0x0b   FIELD_TYPE_TIME
 *                          0x0c   FIELD_TYPE_DATETIME
 *                          0x0d   FIELD_TYPE_YEAR
 *                          0x0e   FIELD_TYPE_NEWDATE
 *                          0x0f   FIELD_TYPE_VARCHAR (new in MySQL 5.0)
 *                          0x10   FIELD_TYPE_BIT (new in MySQL 5.0)
 *                          0xf6   FIELD_TYPE_NEWDECIMAL (new in MYSQL 5.0)
 *                          0xf7   FIELD_TYPE_ENUM
 *                          0xf8   FIELD_TYPE_SET
 *                          0xf9   FIELD_TYPE_TINY_BLOB
 *                          0xfa   FIELD_TYPE_MEDIUM_BLOB
 *                          0xfb   FIELD_TYPE_LONG_BLOB
 *                          0xfc   FIELD_TYPE_BLOB
 *                          0xfd   FIELD_TYPE_VAR_STRING
 *                          0xfe   FIELD_TYPE_STRING
 *                          0xff   FIELD_TYPE_GEOMETRY
 * 
 * flags:                   The possible flag values at time of
 *                          writing (taken from  include/mysql_com.h), in hexadecimal:
 *                          0001 NOT_NULL_FLAG
 *                          0002 PRI_KEY_FLAG
 *                          0004 UNIQUE_KEY_FLAG
 *                          0008 MULTIPLE_KEY_FLAG
 *                          0010 BLOB_FLAG
 *                          0020 UNSIGNED_FLAG
 *                          0040 ZEROFILL_FLAG
 *                          0080 BINARY_FLAG
 *                          0100 ENUM_FLAG
 *                          0200 AUTO_INCREMENT_FLAG
 *                          0400 TIMESTAMP_FLAG
 *                          0800 SET_FLAG
 * 
 * decimals:                The number of positions after the decimal
 *                          point if the type is DECIMAL or NUMERIC.
 *                          Also known as "scale".
 * default:                 For table definitions. Doesn't occur for
 *                          normal result sets. See mysql_list_fields().
 * Alternative Terms: Field Packets are also called "Header Info Packets" or "field descriptor packets" (that's a better term but it's rarely used). In non-MySQL contexts Field Packets are more commonly known as "Result Set Metadata".
 * 
 * Relevant MySQL source code:
 * (client) client/client.c unpack_fields().
 * (server) sql/sql_base.cc send_fields().
 * Example of Field Packet
 *                     Hexadecimal                ASCII
 *                     -----------                -----
 * catalog             03 73 74 64                .std
 * db                  03 64 62 31                .db1
 * table               02 54 37                   .T7
 * org_table           02 74 37                   .t7
 * name                02 53 31                   .S1
 * org_name            02 73 31                   .s1
 * (filler)            0c                         .
 * charsetnr           08 00                      ..
 * length              01 00 00 00                ....
 * type                fe                         .
 * flags               00 00                      ..
 * decimals            00                         .
 * (filler)            00 00                      ..
 * In the example, we see what the server returns for "SELECT s1 AS S1 FROM t7 AS T7" where column s1 is defined as CHAR(1).
 * </pre>
 * 
 * @author xiebiao
 * 
 */
public class FieldPacket extends BasePacket {
	public String catalog = "def";
	public String db = "";
	public String table = "";
	public String orgTable = "";
	public String name = "";
	public String orgName = "";
	public byte fill1 = 0x0c;// 影响client 显示格式
	public int charsetnr = 0x08;
	public long length;
	public byte type;
	public int flag = 0;
	public byte decimals = 0;
	public byte[] fill2 = new byte[2];

	@Override
	public byte[] getBytes() {
		int len = ByteBuffer.getLCStringLen(catalog)
				+ ByteBuffer.getLCStringLen(db)
				+ ByteBuffer.getLCStringLen(table)
				+ ByteBuffer.getLCStringLen(orgTable)
				+ ByteBuffer.getLCStringLen(name)
				+ ByteBuffer.getLCStringLen(orgName) + 13;
		ByteBuffer buf = new ByteBuffer(len);
		buf.putLCString(catalog);
		buf.putLCString(db);
		buf.putLCString(table);
		buf.putLCString(orgTable);
		buf.putLCString(name);
		buf.putLCString(orgName);
		buf.putByte(fill1);
		buf.putUInt16(charsetnr);
		buf.putUInt32(length);
		buf.putByte(type);
		buf.putUInt16(flag);
		buf.putByte(decimals);
		buf.putBytes(fill2);
		return buf.getBytes();
	}

	@Override
	public void putBytes(byte[] bs) {
	}

}
