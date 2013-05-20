package com.github.mybridge.core;

import com.github.jnet.utils.IoBuffer;

/**
 * @author xiebiao
 */
public interface MySQLProtocol {

    public void connected(IoBuffer readBuffer, IoBuffer writeBuffer);

    public void packetReceived(IoBuffer readBuffer, IoBuffer writeBuffer);

    public void packetSended(IoBuffer readBuffer, IoBuffer writeBuffer);

}
