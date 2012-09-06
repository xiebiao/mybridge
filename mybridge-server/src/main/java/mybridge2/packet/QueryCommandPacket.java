package mybridge2.packet;



import org.apache.commons.lang3.builder.ToStringBuilder;
import org.github.mybridge.plugin.netty.PacketBuffer;


/**
 * <一句话功能简述>
 * QueryCommandPacket 解析从客户端发送过来的query语句。
 * <功能详细描述>
 * 
 * @author  zKF36895
 * @version  [版本号, 2011-6-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryCommandPacket extends CommandPacket
{
    private String query;
    
    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public void init(PacketBuffer buffer)
    {
        super.init(buffer);
        byte[] bs = new byte[buffer.getBuffer().readableBytes()];
        buffer.readBytes(bs, 0, bs.length);
        this.query = new String(bs);
        //        System.out.println(query);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("query", this.query)
                .append("command", this.getCommand())
                .append("bufferByteIndex", this.getBufferByteIndex())
                .toString();
    }
    
   
}
