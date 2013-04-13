package com.github.mybridge;

import com.github.jnet.utils.IOBuffer;

public interface MySQLProtocol {

	public void onSessionOpen(IOBuffer readBuffer, IOBuffer writeBuffer);

	public void onPacketReceived(IOBuffer readBuffer, IOBuffer writeBuffer);

	public void onPacketSended(IOBuffer readBuffer, IOBuffer writeBuffer);

	public void onSessionClose();

	public void setPacketId(byte id);

	public byte getPacketId();
}
