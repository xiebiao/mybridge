package com.github.mybridge.core;

import java.util.List;

import com.github.mybridge.core.packet.Packet;

public interface MySQLHandler {

	public List<Packet> execute(Packet cmd) throws ExecuteException;

	public void setCharset(String charset);

	public void setDatabase(String database);

}
