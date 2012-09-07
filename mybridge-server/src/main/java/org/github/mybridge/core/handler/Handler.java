package org.github.mybridge.core.handler;

import java.util.List;

import org.github.mybridge.core.handler.exception.CommandException;
import org.github.mybridge.core.packet.CommandPacket;
import org.github.mybridge.core.packet.Packet;

public interface Handler {

	public List<Packet> executeCommand(CommandPacket cmd)
			throws CommandException;

	public void open();

	public void close();

	public void setCharset(String charset);

	public void setDb(String db);

}
