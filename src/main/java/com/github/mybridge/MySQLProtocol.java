package com.github.mybridge;

import com.github.jnet.utils.IOBuffer;

public interface MySQLProtocol {

	public void connected(IOBuffer readBuffer, IOBuffer writeBuffer);

	public void packetReceived(IOBuffer readBuffer, IOBuffer writeBuffer);

	public void packetSended(IOBuffer readBuffer, IOBuffer writeBuffer);

	public void close();

	public byte getPacketNumber();
}
