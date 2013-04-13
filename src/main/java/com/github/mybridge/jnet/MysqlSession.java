package com.github.mybridge.jnet;

import java.util.List;

import com.github.jnet.IOState;
import com.github.jnet.Session;
import com.github.jnet.utils.IOBuffer;
import com.github.mybridge.MySQLProtocol;
import com.github.mybridge.core.ExecuteException;
import com.github.mybridge.core.Handler;
import com.github.mybridge.core.MySQLCommands;
import com.github.mybridge.core.MySQLHandler;
import com.github.mybridge.core.packet.AuthenticationPacket;
import com.github.mybridge.core.packet.CommandPacket;
import com.github.mybridge.core.packet.ErrorPacket;
import com.github.mybridge.core.packet.HandshakeState;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;
import com.github.mybridge.core.packet.PacketHeader;

public class MysqlSession extends Session {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MysqlSession.class);
	private static int READ_HEADER = 0;
	private static int READ_BODY = 1;
	private int currentState = READ_HEADER;
	private MySQLProtocol mysql;

	public MysqlSession() {
	}

	@Override
	public void open(IOBuffer readBuf, IOBuffer writeBuf) throws Exception {
		this.mysql = new JnetMySQLProtocolImpl(this);
		this.mysql.onSessionOpen(readBuf, writeBuf);
	}

	@Override
	public void readCompleted(IOBuffer readBuf, IOBuffer writeBuf)
			throws Exception {
		if (currentState == READ_HEADER) {
			logger.debug("READ_HEADER");
			PacketHeader header = new PacketHeader();
			header.putBytes(readBuf.readBytes(0, readBuf.limit()));
			this.mysql.setPacketId((byte) (mysql.getPacketId() + 1));
			readBuf.position(0);
			readBuf.limit(header.getPacketLen());
			currentState = READ_BODY;
		} else {
			logger.debug("READ_BODY");
			currentState = READ_HEADER;
			this.mysql.onPacketReceived(readBuf, writeBuf);
		}
	}

	@Override
	public void writeCompleted(IOBuffer readBuf, IOBuffer writeBuf)
			throws Exception {
		this.mysql.onPacketSended(readBuf, writeBuf);
	}

	@Override
	public void close() {
		if (this.mysql != null) {
			this.mysql.onSessionClose();
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
