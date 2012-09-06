package mybridge2.packet;

import java.io.UnsupportedEncodingException;

import org.github.mybridge.plugin.netty.PacketBuffer;


/**
 * <一句话功能简述>
 * Bytes                        Name
 -----                        ----
 1                            protocol_version
 n (Null-Terminated String)   server_version
 4                            thread_id
 8                            scramble_buff
 1                            (filler) always 0x00
 2                            server_capabilities
 1                            server_language
 2                            server_status
 2                            server capabilities (two upper bytes)
 1                            length of the scramble
10                            (filler)  always 0
 n                            rest of the plugin provided data (at least 12 bytes) 
 1                            \0 byte, terminating the second part of a scramble

 protocol_version:    The server takes this from PROTOCOL_VERSION
                      in /include/mysql_version.h. Example value = 10.
 
 server_version:      The server takes this from MYSQL_SERVER_VERSION
                      in /include/mysql_version.h. Example value = "4.1.1-alpha".
 
 thread_number:       ID of the server thread for this connection.
 
 scramble_buff:       The password mechanism uses this. The second part are the
                      last 13 bytes.
                      (See "Password functions" section elsewhere in this document.)
 
 server_capabilities: CLIENT_XXX options. The possible flag values at time of
 writing (taken from  include/mysql_com.h):
  CLIENT_LONG_PASSWORD  1   /* new more secure passwords 
  CLIENT_FOUND_ROWS 2   /* Found instead of affected rows 
  CLIENT_LONG_FLAG  4   /* Get all column flags 
  CLIENT_CONNECT_WITH_DB    8   /* One can specify db on connect 
  CLIENT_NO_SCHEMA  16  /* Don't allow database.table.column 
  CLIENT_COMPRESS       32  /* Can use compression protocol 
  CLIENT_ODBC       64  /* Odbc client 
  CLIENT_LOCAL_FILES    128 /* Can use LOAD DATA LOCAL 
  CLIENT_IGNORE_SPACE   256 /* Ignore spaces before '(' 
  CLIENT_PROTOCOL_41    512 /* New 4.1 protocol 
  CLIENT_INTERACTIVE    1024    /* This is an interactive client 
  CLIENT_SSL              2048  /* Switch to SSL after handshake 
  CLIENT_IGNORE_SIGPIPE   4096    /* IGNORE sigpipes 
  LIENT_TRANSACTIONS   8192    /* Client knows about transactions 
  CLIENT_RESERVED         16384   /* Old flag for 4.1 protocol  
  CLIENT_SECURE_CONNECTION 32768  /* New 4.1 authentication 
  CLIENT_MULTI_STATEMENTS 65536   /* Enable/disable multi-stmt support 
  CLIENT_MULTI_RESULTS    131072  /* Enable/disable multi-results 
 
 server_language:     current server character set number
 
* server_status:       SERVER_STATUS_xxx flags: e.g. SERVER_STATUS_AUTOCOMMIT
 * 
 * <功能详细描述>
 * 
 * @author  zKF36895
 * @version  [版本号, 2011-6-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HandshakePacket extends Packet
{
    private byte protocolVersion;
    
    private String serverVersion;
    
    private long threadId;
    
    private String seed;
    
    private int serverCapabilities;
    
    private byte serverCharsetIndex;
    
    private short serverStatus;
    
    private String restOfScrambleBuff;
    
    public byte getProtocolVersion()
    {
        return protocolVersion;
    }

    public void setProtocolVersion(byte protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    public String getServerVersion()
    {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion)
    {
        this.serverVersion = serverVersion;
    }

    public long getThreadId()
    {
        return threadId;
    }

    public void setThreadId(long threadId)
    {
        this.threadId = threadId;
    }

    public String getSeed()
    {
        return seed;
    }

    public void setSeed(String seed)
    {
        this.seed = seed;
    }

    public int getServerCapabilities()
    {
        return serverCapabilities;
    }

    public void setServerCapabilities(int serverCapabilities)
    {
        this.serverCapabilities = serverCapabilities;
    }

    public byte getServerCharsetIndex()
    {
        return serverCharsetIndex;
    }

    public void setServerCharsetIndex(byte serverCharsetIndex)
    {
        this.serverCharsetIndex = serverCharsetIndex;
    }

    public short getServerStatus()
    {
        return serverStatus;
    }

    public void setServerStatus(short i)
    {
        this.serverStatus = i;
    }

    public String getRestOfScrambleBuff()
    {
        return restOfScrambleBuff;
    }

    public void setRestOfScrambleBuff(String restOfScrambleBuff)
    {
        this.restOfScrambleBuff = restOfScrambleBuff;
    }

    public void write2Buffer(PacketBuffer buffer)
            throws UnsupportedEncodingException
    {
        super.write2Buffer(buffer);
        
        buffer.writeByte(this.protocolVersion);
        buffer.writeNullTerminatedString(this.serverVersion);
        buffer.writeMySQLLong(this.threadId);
        buffer.writeNullTerminatedString(this.seed);
        buffer.writeMySQLInt(this.serverCapabilities & 0xffff);
        buffer.writeByte(this.serverCharsetIndex);
        buffer.writeMySQLInt(this.serverStatus);
        buffer.writeMySQLInt((short)(this.serverCapabilities >> 16));
        buffer.writeBytes(new byte[11]);
        buffer.writeNullTerminatedString(this.restOfScrambleBuff);
        
        super.afterPacketWritten(buffer);
    }
    
}