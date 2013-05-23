package com.github.mybridge.core;

import java.util.List;

import com.github.mybridge.core.packet.AbstractPacket;

/**
 * (线程安全)
 * @author xiebiao
 */
public interface MySQLHandler {

    public List<AbstractPacket> execute(AbstractPacket cmd) throws ExecuteException;

    public void setCharset(String charset);

    public void setDatabase(String database);

}
