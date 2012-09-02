package org.github.mybridge.core.handler;

import java.util.List;

import org.github.mybridge.core.packet.BasePacket;
import org.github.mybridge.core.packet.CommandPacket;



public interface Handler {

	public List<BasePacket> executeCommand(CommandPacket cmd) throws Exception;

	public void open();

	public void close();

	public void setCharset(String charset) throws Exception;

	public void setDb(String db) throws Exception;

}
