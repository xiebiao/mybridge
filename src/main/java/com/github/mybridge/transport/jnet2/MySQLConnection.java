package com.github.mybridge.transport.jnet2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.AbstractConnection;
import com.github.jnet.Handler;
import com.github.mybridge.mysql.packet.HandshakeState;
import com.github.mybridge.mysql.packet.InitialHandshakePacket;
import com.github.mybridge.mysql.packet.PacketHeader;
import com.mysql.jdbc.StringUtils;

public class MySQLConnection extends AbstractConnection {

    private HandshakeState state;
    private Handler        mysqlHandler;
    private String         database;
    private String         charset;
    private static int     READ_HEADER  = 0;
    private static int     READ_BODY    = 1;
    private int            currentState = READ_HEADER;
    private static Logger  logger       = LoggerFactory.getLogger(MySQLConnection.class);

    public MySQLConnection(SocketChannel channel) {
        super(channel);
        this.mysqlHandler = new MySQLHandler(this);
    }

    public void connected() throws IOException {
        state = HandshakeState.WRITE_INIT;
        InitialHandshakePacket initPacket = new InitialHandshakePacket();
        ByteBuffer bb = ByteBuffer.wrap(initPacket.getBytes());
        channel.write(bb);
    }

    @Override
    public void write(ByteBuffer buffer) {

    }

    @Override
    public void write() {

    }

    @Override
    public void read() throws IOException {
        this.readBuffer.limit(readBuffer.position() + bufferMaxSize);
        this.channel.read(this.readBuffer.getBuffer());
        int len = readBuffer.position();
        logger.debug(StringUtils.dumpAsHex(this.readBuffer.getBytes(len), len));

        if (currentState == READ_HEADER) {
            PacketHeader header = new PacketHeader();
            header.putBytes(this.readBuffer.readBytes(0, this.readBuffer.limit()));
            header.packetNumberInc();
            this.readBuffer.position(0);
            this.readBuffer.limit(header.getPacketLen());
            currentState = READ_BODY;
        } else {
            currentState = READ_HEADER;
            // this.mysqlHandler.handle( this.readBuffer.)
            // this.mysql.packetReceived(readBuf, writeBuf);
        }
    }

    @Override
    public boolean close() {
        return false;
    }

}
