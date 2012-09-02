package mybridge2.packet2;

//Handshake Initialization Packet
//From server to client during initial handshake.
//Bytes                        Name
// -----                        ----
// 1                            protocol_version
// n (Null-Terminated String)   server_version
// 4                            thread_id
// 8                            scramble_buff
// 1                            (filler) always 0x00
// 2                            server_capabilities
// 1                            server_language
// 2                            server_status
// 13                           (filler) always 0x00 ...
// 13                           rest of scramble_buff (4.1)
// 
// protocol_version:    The server takes this from PROTOCOL_VERSION
//                      in /include/mysql_version.h. Example value = 10.
// 
// server_version:      The server takes this from MYSQL_SERVER_VERSION
//                      in /include/mysql_version.h. Example value = "4.1.1-alpha".
// 
// thread_number:       ID of the server thread for this connection.
// 
// scramble_buff:       The password mechanism uses this. The second part are the
//                      last 13 bytes.
//                      (See "Password functions" section elsewhere in this document.)
// 
// server_capabilities: CLIENT_XXX opbyte[]tions. The possible flag values at time of
// writing (taken from  include/mysql_com.h):
//  CLIENT_LONG_PASSWORD	1	/* new more secure passwords */
//  CLIENT_FOUND_ROWS	2	/* Found instead of affected rows */
//  CLIENT_LONG_FLAG	4	/* Get all column flags */
//  CLIENT_CONNECT_WITH_DB	8	/* One can specify db on connect */
//  CLIENT_NO_SCHEMA	16	/* Don't allow database.table.column */
//  CLIENT_COMPRESS		32	/* Can use compression protocol */
//  CLIENT_ODBC		64	/* Odbc client */
//  CLIENT_LOCAL_FILES	128	/* Can use LOAD DATA LOCAL */
//  CLIENT_IGNORE_SPACE	256	/* Ignore spaces before '(' */
//  CLIENT_PROTOCOL_41	512	/* New 4.1 protocol */
//  CLIENT_INTERACTIVE	1024	/* This is an interactive client */
//  CLIENT_SSL              2048	/* Switch to SSL after handshake */
//  CLIENT_IGNORE_SIGPIPE   4096    /* IGNORE sigpipes */
//  CLIENT_TRANSACTIONS	8192	/* Client knows about transactions */
//  CLIENT_RESERVED         16384   /* Old flag for 4.1 protocol  */
//  CLIENT_SECURE_CONNECTION 32768  /* New 4.1 authentication */
//  CLIENT_MULTI_STATEMENTS 65536   /* Enable/disable multi-stmt support */
//  CLIENT_MULTI_RESULTS    131072  /* Enable/disable multi-results */
// 
// server_language:     current server character set number
// 
// server_status:       SERVER_STATUS_xxx flags: e.g. SERVER_STATUS_AUTOCOMMIT
// 
//Alternative terms: Handshake Initialization Packet is also called "greeting packet". Protocol version is also called "Prot. version". server_version is also called "Server Version String". thread_number is also called "Thread Number". current server charset number is also called "charset_no". scramble_buff is also called "crypt seed". server_status is also called "SERVER_STATUS_xxx flags" or "Server status variables".
//
//Example Handshake Initialization Packet
//                    Hexadecimal                ASCII
//                    -----------                -----
//protocol_version    0a                         .
//server_version      34 2e 31 2e 31 2d 71 6c    3.1.0-al
//                    70 68 61 2d 64 65 62 75    pha-debu
//                    67 00                      g.
//thread_number       01 00 00 00                ....
//scramble_buff       3a 23 3d 4b 43 4a 2e 43    ........
//(filler)            00                         .
//server_capabilities 2c 82                      ..
//server_language     08                         .
//server_status       02 00                      ..
//(filler)            00 00 00 00 00 00 00 00    ........
//                    00 00 00 00 00
//In the example, the server is telling the client that its server_capabilities include CLIENT_MULTI_RESULTS, CLIENT_SSL, CLIENT_COMPRESS, CLIENT_CONNECT_WITH_DB, CLIENT_FOUND_ROWS.
//
//The "server_language" (or "charset") corresponds to the character_set_server variable in the MySQL server. This number also contains the collation used. Technically this number determines the collation and the character set is implicit for the collation. You can use the following SQL statement to get the cleartext information:
//mysql> SELECT CHARACTER_SET_NAME, COLLATION_NAME
//    -> FROM INFORMATION_SCHEMA.COLLATIONS
//    -> WHERE ID=8;
//+--------------------+-------------------+
//| CHARACTER_SET_NAME | COLLATION_NAME    |
//+--------------------+-------------------+
//| latin1             | latin1_swedish_ci | 
//+--------------------+-------------------+
//1 row in set (0,00 sec)

public class PacketInit extends Packet {
	public static byte[] defaultPacket = { (byte) 0xa, (byte) 0x35, (byte) 0x2e, (byte) 0x31, (byte) 0x2e, (byte) 0x34, (byte) 0x39, (byte) 0x2d, (byte) 0x31, (byte) 0x75, (byte) 0x62, (byte) 0x75, (byte) 0x6e, (byte) 0x74, (byte) 0x75, (byte) 0x38, (byte) 0x2e, (byte) 0x31, (byte) 0x0, (byte) 0x30, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x69, (byte) 0x73, (byte) 0x26, (byte) 0x5f, (byte) 0x30, (byte) 0x6d, (byte) 0x55, (byte) 0x45, (byte) 0x0, (byte) 0xff, (byte) 0xf7, (byte) 0x8, (byte) 0x2, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x37, (byte) 0x23, (byte) 0x47, (byte) 0x7c, (byte) 0x5f, (byte) 0x44, (byte) 0x74, (byte) 0x3e, (byte) 0x76, (byte) 0x57, (byte) 0x34, (byte) 0x3c, (byte) 0x0 };
	public byte protocalVersion = 10;
	public String serverVersion = "5.1.4\0";
	public long threadId = Thread.currentThread().getId();;
	public byte[] scrambleBuff = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 };
	public byte filler1 = 0x0;
	public int serverCapabilities = 63487;
	public byte serverLang = 8;
	public int serverStatus = 0;
	public byte[] filler2 = new byte[13];
	public byte[] lastScrambleBuff = new byte[13];

	@Override
	public byte[] getBytes() {
		int len = 45 + serverVersion.length();
		Buffer buf = new Buffer(len);
		buf.writeByte(protocalVersion);
		buf.writeNullString(serverVersion);
		buf.writeUInt32(threadId);
		buf.writeBytes(scrambleBuff);
		buf.writeByte(filler1);
		buf.writeUInt16(serverCapabilities);
		buf.writeByte(serverLang);
		buf.writeUInt16(serverStatus);
		buf.writeBytes(filler2);
		buf.writeBytes(lastScrambleBuff);
		return buf.getBytes();
	}

	@Override
	public void putBytes(byte[] bs) {
	}
}
