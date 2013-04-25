package com.github.mybridge.jnet;

import com.github.jnet.Session;
import com.github.jnet.utils.IoBuffer;
import com.github.mybridge.core.MySQLProtocol;
import com.github.mybridge.core.packet.PacketHeader;
import com.github.mybridge.engine.Engine;

public class MySQLSession extends Session {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLSession.class);
	private static int READ_HEADER = 0;
	private static int READ_BODY = 1;
	private int currentState = READ_HEADER;
	private MySQLProtocol mysql;
	private static Engine engine;

	public MySQLSession() {
		//engine = new DefaultEngine();
	}

	@Override
	public void open(IoBuffer readBuf, IoBuffer writeBuf) throws Exception {
		this.mysql = new JnetMySQLProtocolImpl(this);
		this.mysql.connected(readBuf, writeBuf);
	}

	@Override
	public void readCompleted(IoBuffer readBuf, IoBuffer writeBuf)
			throws Exception {
		if (currentState == READ_HEADER) {
			PacketHeader header = new PacketHeader();
			header.putBytes(readBuf.readBytes(0, readBuf.limit()));
			header.packetNumberInc();
			readBuf.position(0);
			readBuf.limit(header.getPacketLen());
			currentState = READ_BODY;
		} else {
			currentState = READ_HEADER;
			this.mysql.packetReceived(readBuf, writeBuf);
		}
	}

	@Override
	public void writeCompleted(IoBuffer readBuf, IoBuffer writeBuf)
			throws Exception {
		this.mysql.packetSended(readBuf, writeBuf);
	}

	@Override
	public void close() {
		// this.setNextState(IOState.CLOSE);
	}

	@Override
	public void reading(IoBuffer readBuf, IoBuffer writeBuf) throws Exception {

	}

	@Override
	public void writing(IoBuffer readBuf, IoBuffer writeBuf) throws Exception {

	}

}
