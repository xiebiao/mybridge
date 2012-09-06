package mybridge2.packet;

import java.io.UnsupportedEncodingException;

import org.github.mybridge.plugin.netty.PacketBuffer;


public class ErrorPacket extends Packet
{
    private final byte PACKET_TYPE_ERROR = (byte)0xff;
    
    /*
     * 错误代码
     */
    private int errno = 0;
    
    /**
     * 错误状态-5个字节
     */
    private String sqlstate = "00000";
    
    /**
     * 错误信息
     */
    private String serverErrorMessage="";
    
    public int getErrno()
    {
        return errno;
    }

    public void setErrno(int errno)
    {
        this.errno = errno;
    }

    public String getSqlstate()
    {
        return sqlstate;
    }

    public void setSqlstate(String sqlstate)
    {
        this.sqlstate = sqlstate;
    }

    public String getServerErrorMessage()
    {
        return serverErrorMessage;
    }

    public void setServerErrorMessage(String serverErrorMessage)
    {
        this.serverErrorMessage = serverErrorMessage;
    }

    public byte getPACKET_TYPE_ERROR()
    {
        return PACKET_TYPE_ERROR;
    }

    @Override
    public void write2Buffer(PacketBuffer buffer)
            throws UnsupportedEncodingException
    {
        super.write2Buffer(buffer);
        
        buffer.writeByte(PACKET_TYPE_ERROR);
        buffer.writeMySQLInt(errno);
        buffer.writeByte((byte)(0x23));// 始终为#
        buffer.writeStringNoNull(sqlstate+serverErrorMessage);
        
        super.afterPacketWritten(buffer);
    }
    
}