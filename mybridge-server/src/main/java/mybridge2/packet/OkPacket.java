package mybridge2.packet;

import java.io.UnsupportedEncodingException;

import mybridge2.netty.PacketBuffer;

/**
 * /**
 * From server to client in response to command, if no error and no result set.
 * 
 * <pre>
 * VERSION 4.0
 *  Bytes                       Name
 *  -----                       ----
 *  1   (Length Coded Binary)   field_count, always = 0
 *  1-9 (Length Coded Binary)   affected_rows
 *  1-9 (Length Coded Binary)   insert_id
 *  2                           server_status
 *  n   (until end of packet)   message
 *  
 *  VERSION 4.1
 *  Bytes                       Name
 *  -----                       ----
 *  1   (Length Coded Binary)   field_count, always = 0
 *  1-9 (Length Coded Binary)   affected_rows
 *  1-9 (Length Coded Binary)   insert_id
 *  2                           server_status
 *  2                           warning_count
 *  n   (until end of packet)   message
 *  
 *  field_count:     always = 0
 *  
 *  affected_rows:   = number of rows affected by INSERT/UPDATE/DELETE
 *  
 *  insert_id:       If the statement generated any AUTO_INCREMENT number, 
 *                   it is returned here. Otherwise this field contains 0.
 *                   Note: when using for example a multiple row INSERT the
 *                   insert_id will be from the first row inserted, not from
 *                   last.
 *  
 *  server_status:   = The client can use this to check if the
 *                   command was inside a transaction.
 *  
 *  warning_count:   number of warnings
 *  
 *  message:         For example, after a multi-line INSERT, message might be
 *                   &quot;Records: 3 Duplicates: 0 Warnings: 0&quot;
 * ========================================================================
 *</pre>
 * 
 *<pre>
 *  The message field is optional. 
 *  Alternative terms: OK Packet is also known as &quot;okay packet&quot; or &quot;ok packet&quot; or &quot;OK-Packet&quot;. 
 *  field_count is also known as &quot;number of rows&quot; or &quot;marker for ok packet&quot;. 
 *  message is also known as &quot;Messagetext&quot;. 
 *  OK Packets (and result set packets) are also called &quot;Result packets&quot;.
 *</pre>
 * 
 *<pre>
 * ===============================================================
 * Example OK Packet
 *                     Hexadecimal                ASCII
 *                     -----------                -----
 * field_count         00                         .
 * affected_rows       01                         .
 * insert_id           00                         .
 * server_status       02 00                      ..
 * warning_count       00 00                      ..
 *</pre>
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zKF36895
 * @version  [版本号, 2011-6-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OkPacket extends Packet
{
    private final byte PACKET_TYPE_OK = 0x00;
    
    private long affectedRows = 0;
    
    private long insertId = 0;
    
    private int serverStatus = 2;
    
    private int warningCount = 0;
    
    private String message;
    
    public byte getPACKET_TYPE_OK()
    {
        return PACKET_TYPE_OK;
    }
    
    public long getAffectedRows()
    {
        return affectedRows;
    }
    
    public void setAffectedRows(long affectedRows)
    {
        this.affectedRows = affectedRows;
    }
    
    public long getInsertId()
    {
        return insertId;
    }
    
    public void setInsertId(long insertId)
    {
        this.insertId = insertId;
    }
    
    public int getServerStatus()
    {
        return serverStatus;
    }
    
    public void setServerStatus(int serverStatus)
    {
        this.serverStatus = serverStatus;
    }
    
    public int getWarningCount()
    {
        return warningCount;
    }
    
    public void setWarningCount(int warningCount)
    {
        this.warningCount = warningCount;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(int rowsMatched, int changed, int warnings)
    {
        //        String message=
        StringBuffer buffer = new StringBuffer(8);
        buffer.append("Rows matched: ").append(rowsMatched);
        buffer.append("  Changed: ").append(changed);
        buffer.append("Warnings: ").append(warnings);
        this.message = buffer.toString();
    }
    
    public void write2Buffer(PacketBuffer buffer)
            throws UnsupportedEncodingException
    {
        super.write2Buffer(buffer);
        buffer.writeByte(PACKET_TYPE_OK);
        buffer.writeFieldLength(this.affectedRows);
        buffer.writeFieldLength(this.insertId);
        buffer.writeMySQLInt(this.serverStatus);
        buffer.writeMySQLInt(this.warningCount);
        if (this.message != null)
        {
            buffer.writeStringNoNull(this.message);
        }
        super.afterPacketWritten(buffer);
    }
    
}