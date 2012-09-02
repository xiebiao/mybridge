package org.github.mybridge.core.packet;

import java.security.MessageDigest;

import org.github.mybridge.core.buffer.ByteBuffer;


/**
 * <pre>
 * From client to server during initial handshake.
 * 
 * VERSION 4.0
 * Bytes                        Name
 * -----                        ----
 * 2                            client_flags
 * 3                            max_packet_size
 * n  (Null-Terminated String)  user
 * 8                            scramble_buff
 * 1                            (filler) always 0x00
 * 
 * VERSION 4.1
 * Bytes                        Name
 * -----                        ----
 * 4                            client_flags
 * 4                            max_packet_size
 * 1                            charset_number
 * 23                           (filler) always 0x00...
 * n  (Null-Terminated String)  user
 * 8                            scramble_buff
 * 1                            (filler) always 0x00
 * n (Null-Terminated String)   databasename
 * 
 * client_flags:            CLIENT_xxx options. The list of possible flag
 *                          values is in the description of the Handshake
 *                          Initialisation Packet, for server_capabilities.
 *                          For some of the bits, the server passed "what
 *                          it's capable of". The client leaves some of the
 *                          bits on, adds others, and passes back to the server.
 *                          One important flag is: whether compression is desired.
 * 
 * max_packet_size:         the maximum number of bytes in a packet for the client
 * 
 * charset_number:          in the same domain as the server_language field that
 *                          the server passes in the Handshake Initialisation packet.
 * 
 * user:                    identification
 * 
 * scramble_buff:           the password, after encrypting using the scramble_buff
 *                          contents passed by the server (see "Password functions"
 *                          section elsewhere in this document)
 * 
 * databasename:            name of schema to use initially
 * The scramble_buff and databasename fields are optional. The length-coding byte for the scramble_buff will always be given, even if it's zero.
 * 
 * Alternative terms: "Client authentication packet" is sometimes called "client auth response" or "client auth packet" or "login packet". "Scramble_buff" is sometimes called "crypted password".
 * </pre>
 * 
 * @author xiaog
 * 
 */
public class AuthenticationPacket extends BasePacket {

	public long clientFlag;
	public long maxPacketSize;
	public byte charsetNum;
	public byte[] filler;
	public String user;// from client user name
	public byte[] pass;// from client pass
	public String dbName;// from client schema

	private String serverName = "root";// the server name
	private String serverPass = "yes";// the server pass

	@Override
	public byte[] getBytes() {
		return null;
	}

	@Override
	public void putBytes(byte[] bytes) {
		ByteBuffer buf = new ByteBuffer(bytes);
		clientFlag = buf.readUInt32();
		maxPacketSize = buf.readUInt32();
		charsetNum = buf.readByte();
		filler = buf.readBytes(23);
		user = buf.readNullString();
		pass = buf.readLCBytes();
		dbName = buf.readNullString();
	}

	public boolean checkAuth(String clientUser, byte[] cPass) throws Exception {
		if (!serverName.equals(clientUser)) {
			return false;
		}
		if (cPass.length == 0 && serverPass.length() == 0) {
			return true;
		}
		byte[] sPass = encodePassword(serverPass);
		if (cPass.length != sPass.length) {
			return false;
		}
		for (int i = 0; i < sPass.length; i++) {
			if (cPass[i] != sPass[i]) {
				return false;
			}
		}
		return true;
	}

	byte[] encodePassword(String password) throws Exception {
		MessageDigest md;
		byte[] seed = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 };
		md = MessageDigest.getInstance("SHA-1");

		byte[] passwordHashStage1 = md.digest(password.getBytes("ASCII"));
		md.reset();

		byte[] passwordHashStage2 = md.digest(passwordHashStage1);
		md.reset();
		md.update(seed);
		md.update(passwordHashStage2);

		byte[] toBeXord = md.digest();

		int numToXor = toBeXord.length;

		for (int i = 0; i < numToXor; i++) {
			toBeXord[i] = (byte) (toBeXord[i] ^ passwordHashStage1[i]);
		}

		return toBeXord;
	}

}
