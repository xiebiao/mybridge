package com.github.mybridge.transport.jnet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.github.jnet.Session.IoState;
import com.github.jnet.utils.IoBuffer;
import com.github.mybridge.core.ExecuteException;
import com.github.mybridge.core.MySQLCommand;
import com.github.mybridge.core.MySQLProtocol;
import com.github.mybridge.mysql.packet.AbstractPacket;
import com.github.mybridge.mysql.packet.AuthenticationPacket;
import com.github.mybridge.mysql.packet.CommandsPacket;
import com.github.mybridge.mysql.packet.EofPacket;
import com.github.mybridge.mysql.packet.ErrPacket;
import com.github.mybridge.mysql.packet.FieldDescriptionPacket;
import com.github.mybridge.mysql.packet.HandshakeState;
import com.github.mybridge.mysql.packet.InitialHandshakePacket;
import com.github.mybridge.mysql.packet.OkPacket;
import com.github.mybridge.mysql.packet.PacketHeader;
import com.github.mybridge.mysql.packet.ResultSetPacket;
import com.github.mybridge.mysql.packet.RowDataPacket;
import com.github.mybridge.sharding.ConnectionPool;
import com.github.mybridge.sharding.support.SimpleConnectionPool;
import com.mysql.jdbc.StringUtils;

public class JnetMySQLProtocolImpl implements MySQLProtocol {

    private JnetMySQLSession              session;
    private HandshakeState                state;
    private String                        charset = "utf-8";
    private String                        database;
    private static final ConnectionPool   cp      = new SimpleConnectionPool();
    private static final org.slf4j.Logger logger  = org.slf4j.LoggerFactory.getLogger(JnetMySQLProtocolImpl.class);

    public JnetMySQLProtocolImpl(JnetMySQLSession session) {
        this.session = session;
        state = HandshakeState.WRITE_INIT;
    }

    @Override
    public void connected(IoBuffer readBuffer, IoBuffer writeBuffer) {
        InitialHandshakePacket init = new InitialHandshakePacket();
        init.packetNumberInc();
        writePacket(writeBuffer, init);
    }

    @Override
    public void packetReceived(IoBuffer readBuffer, IoBuffer writeBuffer) {
        String msg = "";
        ErrPacket errPacket = null;
        switch (state) {
            case READ_AUTH:
                state = HandshakeState.WRITE_RESULT;
                AuthenticationPacket authPacket = new AuthenticationPacket();
                byte[] authByte = readBuffer.getBytes(0, readBuffer.limit());
                logger.debug(StringUtils.dumpAsHex(authByte, authByte.length));
                authPacket.putBytes(authByte);
                String user = "";
                if (authPacket.clientUser.length() > 1) {
                    user = authPacket.clientUser.substring(0, authPacket.clientUser.length() - 1);
                }
                try {
                    if (MySQLCommand.index2Charset.containsKey((int) authPacket.charsetNum)) {
                        charset = MySQLCommand.index2Charset.get((int) authPacket.charsetNum);
                    }
                    if (authPacket.databaseName.length() > 0) {
                        database = authPacket.databaseName.substring(0, authPacket.databaseName.length() - 1);
                    }
                    if (authPacket.checkAuth(user, authPacket.clientPassword)) {
                        OkPacket okPacket = new OkPacket();
                        this.writePacket(writeBuffer, okPacket);
                        break;
                    }
                } catch (Exception e) {
                    msg = "handshake authpacket failed  ";
                    errPacket = new ErrPacket(1045, msg);
                    this.writePacket(writeBuffer, errPacket);
                    state = HandshakeState.CLOSE;
                    break;
                }
                msg = "Access denied for user " + authPacket.clientUser;
                errPacket = new ErrPacket(1045, msg);
                this.writePacket(writeBuffer, errPacket);
                state = HandshakeState.CLOSE;
                break;
            case READ_COMMOND:
                state = HandshakeState.WRITE_RESULT;
                CommandsPacket cmdPacket = new CommandsPacket();
                byte[] bytes = readBuffer.getBytes(0, readBuffer.limit());
                cmdPacket.putBytes(bytes);
                List<AbstractPacket> resultList = null;
                try {
                    resultList = execute(cmdPacket);
                } catch (ExecuteException e) {
                    e.printStackTrace();
                    errPacket = new ErrPacket(1046, "server error");
                    writePacket(writeBuffer, errPacket);
                }
                if (resultList != null && resultList.size() > 0) {
                    writePacketList(writeBuffer, resultList);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void packetSended(IoBuffer readBuffer, IoBuffer writeBuffer) {
        switch (state) {
            case WRITE_INIT:
                state = HandshakeState.READ_AUTH;
                readPacket(readBuffer);
                break;
            case WRITE_RESULT:
                state = HandshakeState.READ_COMMOND;
                readPacket(readBuffer);
                break;
            case CLOSE:
                break;
            default:
        }
    }

    /** ------------------------------------------ private */
    private List<AbstractPacket> execute(String sql) throws SQLException {
        List<AbstractPacket> packetList = new ArrayList<AbstractPacket>();
        // Engine切入点
        Connection connection = cp.getConnection();
        boolean result;
        Statement statement;
        try {
            statement = connection.createStatement();
            result = statement.execute(sql);
        } catch (SQLException e) {
            ErrPacket err = new ErrPacket(e.getErrorCode(), e.getSQLState(), e.getMessage());
            packetList.add(err);
            return packetList;
        }
        if (result == false) {
            OkPacket ok = new OkPacket();
            ok.setAffectedRows(statement.getUpdateCount());
            packetList.add(ok);
            return packetList;
        }
        ResultSet rs = statement.getResultSet();
        ResultSetMetaData meta = rs.getMetaData();
        ResultSetPacket resultPacket = new ResultSetPacket(meta.getColumnCount());
        packetList.add(resultPacket);
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            FieldDescriptionPacket fieldPacket = new FieldDescriptionPacket();
            fieldPacket.setDatabase(meta.getCatalogName(i));
            fieldPacket.setTable(meta.getTableName(i));
            fieldPacket.setOrgTable(meta.getTableName(i));
            fieldPacket.setName(meta.getColumnName(i));
            fieldPacket.setOrgName(meta.getColumnName(i));
            fieldPacket.setType((byte) MySQLCommand.javaTypeToMysql(meta.getColumnType(i)));
            fieldPacket.setLength(meta.getColumnDisplaySize(i));
            packetList.add(fieldPacket);
        }
        packetList.add(new EofPacket());
        while (rs.next()) {
            RowDataPacket rowPacket = new RowDataPacket(charset);
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String value = rs.getString(i);
                rowPacket.addValue(value);
            }
            packetList.add(rowPacket);
        }
        packetList.add(new EofPacket());
        rs.close();
        statement.close();
        connection.close();
        DbUtils.closeQuietly(connection, statement, rs);
        return packetList;
    }

    private List<AbstractPacket> executeSQL(String sql) {
        List<AbstractPacket> packetList = new ArrayList<AbstractPacket>();
        try {
            packetList = execute(sql);
        } catch (Exception e) {
            packetList.add(new ErrPacket());
        }
        return packetList;
    }

    private List<AbstractPacket> execute(AbstractPacket packet) throws ExecuteException {
        List<AbstractPacket> packetList = null;
        CommandsPacket cmdPacket = (CommandsPacket) packet;
        int cmdType = cmdPacket.getType();
        try {
            packetList = new ArrayList<AbstractPacket>();
            switch (cmdType) {
                case MySQLCommand.COM_QUERY:
                    String sql = new String(cmdPacket.getValue(), charset);
                    logger.debug("COM_QUERY: " + sql);
                    return executeSQL(sql);
                case MySQLCommand.COM_QUIT:
                    return null;
                case MySQLCommand.COM_FIELD_LIST:
                    packetList.add(new EofPacket());
                    return packetList;
                case MySQLCommand.COM_INIT_DB:
                    String db = new String(cmdPacket.getValue(), charset);
                    sql = "USE" + db;
                    logger.debug("COM_INIT_DB: " + db);
                    this.database = db;
                    return executeSQL(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecuteException("Command excute error");
        }
        return packetList;
    }

    private void readPacket(IoBuffer readBuf) {
        readBuf.position(0);
        readBuf.limit(4);
        session.setNextState(IoState.READ);
    }

    private void writePacketList(IoBuffer writeBuf, List<AbstractPacket> resultlist) {
        writeBuf.position(0);
        for (AbstractPacket packet : resultlist) {
            byte[] body = packet.getBytes();
            PacketHeader header = new PacketHeader();
            header.setPacketLen(body.length);
            header.packetNumberInc();
            writeBuf.writeBytes(header.getBytes());
            writeBuf.writeBytes(body);
        }
        writeBuf.limit(writeBuf.position());
        writeBuf.position(0);
        session.setNextState(IoState.WRITE);
    }

    private void writePacket(IoBuffer writeBuf, AbstractPacket packet) {
        byte[] body = packet.getBytes();
        PacketHeader header = new PacketHeader();
        header.setPacketLen(body.length);
        header.packetNumberInc();
        writeBuf.position(0);
        writeBuf.writeBytes(header.getBytes());
        writeBuf.writeBytes(body);
        writeBuf.limit(writeBuf.position());
        writeBuf.position(0);
        // logger.debug(StringUtils.toString(body));
        this.session.setNextState(IoState.WRITE);
    }
}
