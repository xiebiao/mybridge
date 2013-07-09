package com.github.mybridge.mysql.packet;

/**
 * <pre>
 * From client to server, to execute a prepared statement.
 * 
 * Bytes                Name
 *  -----                ----
 *  1                    code
 *  4                    statement_id
 *  1                    flags
 *  4                    iteration_count
 *    if param_count > 0:
 *  (param_count+7)/8    null_bit_map
 *  1                    new_parameter_bound_flag
 *    if new_params_bound == 1:
 *  n*2                  type of parameters
 *  n                    values for the parameters 
 *  
 *  code:          always COM_EXECUTE
 *  
 *  statement_id:  statement identifier
 *  
 *  flags:         reserved for future use. In MySQL 4.0, always 0.
 *                 In MySQL 5.0: 
 *                   0: CURSOR_TYPE_NO_CURSOR
 *                   1: CURSOR_TYPE_READ_ONLY
 *                   2: CURSOR_TYPE_FOR_UPDATE
 *                   4: CURSOR_TYPE_SCROLLABLE
 *  
 *  iteration_count: reserved for future use. Currently always 1.
 *  
 *  null_bit_map:  A bitmap indicating parameters that are NULL.
 *                 Bits are counted from LSB, using as many bytes
 *                 as necessary ((param_count+7)/8)
 *                 i.e. if the first parameter (parameter 0) is NULL, then
 *                 the least significant bit in the first byte will be 1.
 *  
 *  new_parameter_bound_flag:   Contains 1 if this is the first time
 *                              that "execute" has been called, or if
 *                              the parameters have been rebound.
 *  
 *  type:          Occurs once for each parameter; 
 *                 The highest significant bit of this 16-bit value
 *                 encodes the unsigned property. The other 15 bits
 *                 are reserved for the type (only 8 currently used).
 *                 This block is sent when parameters have been rebound
 *                 or when a prepared statement is executed for the 
 *                 first time.
 * 
 *  values:        for all non-NULL values, each parameters appends its value
 *                 as described in Row Data Packet: Binary (column values)
 * The Execute Packet is also known as "COM_EXECUTE Packet".
 * 
 * In response to an Execute Packet, the server should send back one of: an OK Packet, an Error Packet, or a series of Result Set Packets in which all the Row Data Packets are binary.
 * 
 * Relevant MySQL Source Code: libmysql/libmysql.c cli_read_prepare_result()
 * </pre>
 * @author xiebiao
 */
public class ExecutePacket extends AbstractPacket implements Packet {

    @Override
    public byte[] getBytes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putBytes(byte[] bytes) {
        // TODO Auto-generated method stub

    }

}
