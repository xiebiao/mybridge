package mybridge2.packet;

import java.util.Arrays;

import mybridge2.netty.PacketBuffer;
import mybridge2.util.Constants;

import org.apache.log4j.Logger;

/**
 * From client to server during initial handshake.
 * 
 * <pre>
 * 
 *  &lt;b&gt;VERSION 4.0&lt;/b&gt;
 *  Bytes                        Name
 *  -----                        ----
 *  2                            client_flags
 *  3                            max_packet_size
 *  n  (Null-Terminated String)  user
 *  8                            scramble_buff
 *  1                            (filler) always 0x00
 *  
 *  &lt;b&gt;VERSION 4.1&lt;/b&gt;
 *  Bytes                        Name
 *  -----                        ----
 *  4                            client_flags
 *  4                            max_packet_size
 *  1                            charset_number
 *  23                           (filler) always 0x00...
 *  n (Null-Terminated String)   user
 *  n (Length Coded Binary)      scramble_buff (1 + x bytes)
 *  1                            (filler) always 0x00
 *  n (Null-Terminated String)   databasename
 *  
 *  client_flags:            CLIENT_xxx options. The list of possible flag
 *                           values is in the description of the Handshake
 *                           Initialisation Packet, for server_capabilities.
 *                           For some of the bits, the server passed &quot;what
 *                           it's capable of&quot;. The client leaves some of the
 *                           bits on, adds others, and passes back to the server.
 *                           One important flag is: whether compression is desired.
 *  
 *  max_packet_size:         the maximum number of bytes in a packet for the client
 *  
 *  charset_number:          in the same domain as the server_language field that
 *                           the server passes in the Handshake Initialization packet.
 *  
 *  user:                    identification
 *  
 *  scramble_buff:           the password, after encrypting using the scramble_buff
 *                           contents passed by the server (see &quot;Password functions&quot;
 *                           section elsewhere in this document)
 *                           if length is zero, no password was given
 *  
 *  databasename:            name of schema to use initially
 * 
 * </pre>
 * 
 * 该数据包只支持mysql 4.1版本以后,mysql协议版本10，并且是非安全连接 <一句话功能简述> JBBC发送过来的loging packet
 * <功能详细描述>
 * 
 * @author zKF36895
 * @version [版本号, 2011-6-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthenticationPacket extends Packet {

	Logger logger = Logger.getLogger(this.getClass());

	private int clientParam;

	private int maxPacketBytes;

	private byte charsetNumber;

	private String user;

	private String seed;

	private String password;

	// 16个字节
	private byte[] encryptedPassword;

	// public byte[] encryptedPassword = Security.scramble411(this.password,
	// this.seed);
	private String database;

	public int getClientParam() {
		return clientParam;
	}

	public void setClientParam(int clientParam) {
		this.clientParam = clientParam;
	}

	public int getMaxPacketBytes() {
		return maxPacketBytes;
	}

	public void setMaxPacketBytes(int maxPacketBytes) {
		this.maxPacketBytes = maxPacketBytes;
	}

	public byte getCharsetNumber() {
		return charsetNumber;
	}

	public void setCharsetNumber(byte charsetNumber) {
		this.charsetNumber = charsetNumber;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(byte[] encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public AuthenticationPacket(String password) {
		this.charsetNumber = 8;
		this.password = password;
	}

	@Override
	public void init(PacketBuffer buffer) {
		super.init(buffer);
		this.clientParam = Integer.reverse(buffer.readInt());
		// maxPacketBytes需要反序
		// this.maxPacketBytes = buffer.readInt();
		this.maxPacketBytes = Integer.reverse(buffer.readInt());
		this.charsetNumber = buffer.readByte();
		buffer.skip(23);
		this.user = buffer.readNullTerminatedString();
		long passwordLength = buffer.readFieldLength();

		this.encryptedPassword = buffer
				.getAuthencryptedPassword(passwordLength);
		if ((clientParam & Constants.CLIENT_CONNECT_WITH_DB) != 0) {
			// 连接字符串中不带数据库的时候不会走这里.
			if (buffer.getBufferByteIndex() < buffer.getBufferlength()) {
				database = buffer.readNullTerminatedString();
			}
		}
	}

	@Override
	public String toString() {
		return "AuthenticationPacket [charsetNumber=" + charsetNumber
				+ ", clientParam=" + clientParam + ", database=" + database
				+ ", encryptedPassword=" + Arrays.toString(encryptedPassword)
				+ ", logger=" + logger + ", maxPacketBytes=" + maxPacketBytes
				+ ", password=" + password + ", seed=" + seed + ", user="
				+ user + "]";
	}

}