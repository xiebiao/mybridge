package org.github.mybridge.core.handler;

import java.util.List;

import org.github.mybridge.core.packet.BasePacket;
import org.github.mybridge.core.packet.PacketCommand;



public interface Handler {

	public List<BasePacket> executeCommand(PacketCommand cmd) throws Exception;

	public void open();

	public void close();

	public void setCharset(String charset) throws Exception;

	public void setDb(String db) throws Exception;

}
