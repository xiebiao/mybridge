package com.github.mybridge.core;

import java.util.List;

import com.github.mybridge.core.packet.Packet;

public interface Handler {

	public List<Packet> execute(Packet cmd) throws CommandExecuteException;

	public void setCharset(String charset);

	public void setDb(String db);

}
