package com.github.mybridge.jnet;

import com.github.jnet.Session;
import com.github.jnet.utils.IOBuffer;
import com.github.mybridge.MySQLProtocol;
import com.github.mybridge.core.packet.PacketHeader;

public class MySQLSession extends Session {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLSession.class);
	private static int READ_HEADER = 0;
	private static int READ_BODY = 1;
	private int currentState = READ_HEADER;
	private MySQLProtocol mysql;

	public MySQLSession() {
	}

	@Override
	public void open(IOBuffer readBuf, IOBuffer writeBuf) throws Exception {
		this.mysql = new JnetMySQLProtocolImpl(this);
		this.mysql.connected(readBuf, writeBuf);
	}

	@Override
	public void readCompleted(IOBuffer readBuf, IOBuffer writeBuf)
			throws Exception {
		if (currentState == READ_HEADER) {
			PacketHeader header = new PacketHeader();
			header.putBytes(readBuf.readBytes(0, readBuf.limit()));
			//this.mysql.setPacketNumber((byte) (mysql.getPacketNumber() + 1));
			readBuf.position(0);
			readBuf.limit(header.getPacketLen());
			currentState = READ_BODY;
		} else {
			currentState = READ_HEADER;
			this.mysql.packetReceived(readBuf, writeBuf);
		}
	}

	@Override
	public void writeCompleted(IOBuffer readBuf, IOBuffer writeBuf)
			throws Exception {
		this.mysql.packetSended(readBuf, writeBuf);
	}

	@Override
	public void close() {
		if (this.mysql != null) {
			this.mysql.close();
		}
	}

	@Override
	public void reading(IOBuffer readBuf, IOBuffer writeBuf) throws Exception {

	}

	@Override
	public void writing(IOBuffer readBuf, IOBuffer writeBuf) throws Exception {
		// TODO Auto-generated method stub

	}

}
