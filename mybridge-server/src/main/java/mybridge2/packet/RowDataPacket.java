package mybridge2.packet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mybridge2.netty.PacketBuffer;
import mybridge2.util.Constants;

/**
 * @version 1.0 2011-6-7
 * @author zKF36895
 */
public class RowDataPacket extends Packet
{
    private List<String> columns=new ArrayList<String>();
    
    @Override
    public void write2Buffer(PacketBuffer buffer)
            throws UnsupportedEncodingException
    {
        super.write2Buffer(buffer);
        
        if (columns == null || columns.size() == 0)
            return;
        
        for (String s : columns)
        {
            buffer.writeLengthCodedString(s, Constants.ENCODING);
        }
        
        super.afterPacketWritten(buffer);
    }
    public void addColumn(String s)
    {
        columns.add(s);
    }
    
}