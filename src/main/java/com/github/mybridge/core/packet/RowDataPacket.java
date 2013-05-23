package com.github.mybridge.core.packet;

import java.util.ArrayList;
import java.util.List;

import com.github.mybridge.core.buffer.ByteBuffer;
import com.github.mybridge.utils.CharsetUtils;

/**
 * <pre>
 * From server to client. One packet for each row in the result set.
 * 
 * Bytes                   Name
 *  -----                   ----
 *  n (Length Coded String) (column value)
 *  ...
 *  
 *  (column value):       The data in the column, as a character string.
 *                        If a column is defined as non-character, the
 *                        server converts the value into a character
 *                        before sending it. Since the value is a Length
 *                        Coded String, a NULL can be represented with a
 *                        single byte containing 251(see the description
 *                        of Length Coded Strings in section "Elements" above).
 * The (column value) fields occur multiple times. All (column value) fields are in one packet. There is no space between each (column value).
 * 
 * Alternative Terms: Row Data Packets are also called "Row Packets" or "Data Packets".
 * 
 * Relevant MySQL source code:
 * (client) client/client.c read_rows
 * Example of Row Data Packet
 *                     Hexadecimal                ASCII
 *                     -----------                -----
 * (first column)      01 58                      .X
 * (second column)     02 35 35                   .55
 * In the example, we see what the packet contains after a SELECT from a table defined as "(s1 CHAR, s2 INTEGER)" and containing one row where s1='X' and s2=55.
 * </pre>
 * @author xiebiao
 */
public class RowDataPacket extends AbstractPacket implements Packet {

    private String       charset   = "utf-8";
    private List<String> valueList = new ArrayList<String>();

    public RowDataPacket(String charset) {
        this.charset = charset;
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        for (String value : valueList) {
            len += ByteBuffer.getLCStringLen(value, charset);
        }
        ByteBuffer buf = new ByteBuffer(len);
        for (String value : valueList) {
            buf.putLCString(value, CharsetUtils.getCharset(value));// 编码
        }
        return buf.getBytes();
    }

    public void addValue(String value) {
        valueList.add(value);
    }

    @Override
    public void putBytes(byte[] bs) {}

}
