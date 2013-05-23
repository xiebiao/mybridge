package com.github.mybridge.transport.netty;

import java.util.List;

import org.jboss.netty.channel.Channel;

import com.github.mybridge.core.DefaultMySQLHandler;
import com.github.mybridge.core.ExecuteException;
import com.github.mybridge.core.MySQLCommand;
import com.github.mybridge.core.MySQLHandler;
import com.github.mybridge.core.packet.AuthenticationPacket;
import com.github.mybridge.core.packet.CommandsPacket;
import com.github.mybridge.core.packet.ErrPacket;
import com.github.mybridge.core.packet.HandshakeState;
import com.github.mybridge.core.packet.InitialHandshakePacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.AbstractPacket;

public class NettyMySQLProtocolImpl {

    private static final org.slf4j.Logger logger  = org.slf4j.LoggerFactory.getLogger(NettyMySQLProtocolImpl.class);
    private HandshakeState                state;
    private String                        charset = "utf-8";
    private String                        database;

    private MySQLHandler                  handler;

    public NettyMySQLProtocolImpl() {
        handler = new DefaultMySQLHandler();
    }

    public void onConnected(Channel channel) {
        state = HandshakeState.WRITE_INIT;
        InitialHandshakePacket initPacket = new InitialHandshakePacket();
        channel.write(initPacket.getBytes());
    }

    public void writeCompleted() {
        switch (state) {
            case WRITE_INIT:
                state = HandshakeState.READ_AUTH;
                break;
            case WRITE_RESULT:
                state = HandshakeState.READ_COMMOND;
            case CLOSE:
                break;
            default:
        }
    }

    public void onRequestReceived(Channel channel, byte[] bytes) {
        String msg = "";
        ErrPacket errPacket = null;
        switch (state) {
            case READ_AUTH:
                state = HandshakeState.WRITE_RESULT;
                AuthenticationPacket auth = new AuthenticationPacket();
                auth.putBytes(bytes);

                String user = "";
                if (auth.clientUser.length() > 1) {
                    user = auth.clientUser.substring(0, auth.clientUser.length() - 1);
                }
                try {
                    if (MySQLCommand.index2Charset.containsKey((int) auth.charsetNum)) {
                        this.charset = MySQLCommand.index2Charset.get((int) auth.charsetNum);
                    }
                    if (auth.databaseName.length() > 0) {
                        String dbname = auth.databaseName.substring(0, auth.databaseName.length() - 1);
                        this.database = dbname;
                    }
                    if (auth.checkAuth(user, auth.clientPassword)) {
                        OkPacket ok = new OkPacket();
                        channel.write(ok.getBytes());
                        break;
                    }
                } catch (Exception e) {
                    msg = "handshake authpacket failed  ";
                    errPacket = new ErrPacket(1045, msg);
                    channel.write(errPacket.getBytes());
                    state = HandshakeState.CLOSE;
                    break;
                }
                msg = "Access denied for user " + auth.clientUser;
                errPacket = new ErrPacket(1045, msg);
                channel.write(errPacket.getBytes());
                state = HandshakeState.CLOSE;
                break;
            case READ_COMMOND:
                state = HandshakeState.WRITE_RESULT;
                CommandsPacket cmd = new CommandsPacket();
                cmd.putBytes(bytes);
                List<AbstractPacket> resultlist = null;
                try {
                    resultlist = handler.execute(cmd);
                } catch (ExecuteException e) {
                    e.printStackTrace();
                    errPacket = new ErrPacket(1046, "server error");
                    channel.write(errPacket);
                }
                if (resultlist != null && resultlist.size() > 0) {
                    writePacketList(channel, resultlist);
                }
                break;
            default:
                break;
        }
    }

    private void writePacketList(Channel channel, List<AbstractPacket> resultlist) {
        for (AbstractPacket packet : resultlist) {
            byte[] temp = packet.getBytes();
            channel.write(temp);
        }
    }
}
