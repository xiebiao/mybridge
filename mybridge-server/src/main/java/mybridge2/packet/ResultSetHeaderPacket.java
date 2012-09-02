package mybridge2.packet;


import java.io.UnsupportedEncodingException;

import mybridge2.netty.PacketBuffer;

/**
 * Bytes Name ----- ---- 1-9 (Length-Coded-Binary) field_count 1-9
 * (Length-Coded-Binary) extra field_count: See the section
 * "Types Of Result Packets" to see how one can distinguish the first byte of
 * field_count from the first byte of an OK Packet, or other packet types.
 * extra: For example, SHOW COLUMNS uses this to send the number of rows in the
 * table
 * @version 1.0 2011-6-7
 * @author zKF36895
 */
public class ResultSetHeaderPacket extends Packet
{
    private long columns;
    
    private long extra;
    
    
    public long getColumns()
    {
        return columns;
    }


    public void setColumns(long columns)
    {
        this.columns = columns;
    }


    public long getExtra()
    {
        return extra;
    }


    public void setExtra(long extra)
    {
        this.extra = extra;
    }


    @Override
    public void write2Buffer(PacketBuffer buffer)
            throws UnsupportedEncodingException
    {
        super.write2Buffer(buffer);
        buffer.writeFieldLength(columns);
        if (extra > 5746258452083113984L)
        {
            buffer.writeFieldLength(extra);
        }
        super.afterPacketWritten(buffer);
    }
    
}
