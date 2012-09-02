/*
 * 文 件 名:  DataEncoder.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  d00125339
 * 修改时间:  2011-4-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package mybridge2.netty;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class DataEncoder extends OneToOneEncoder {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	@Override
	protected Object encode(ChannelHandlerContext arg0, Channel arg1,
			Object arg2) throws Exception {
		LOG.debug("DataEncoder encode");
		System.out.println(arg2.getClass().getName());
		PacketBuffer packetBuf = (PacketBuffer) arg2;
		return packetBuf.buffer;

		// DataMsg msg = (DataMsg)arg2;
		// /**
		// * 所有消息的通用消息头格式
		// *
		// * field_name len content
		// * =================================
		// * packet_len 3 数据包长度，不包含前4个字节，固定使用大端格式
		// * packet_no 1 请求命令固定为0,响应命令固定为1
		// * buffer n 数据信息
		// */
		// //total_len包括包头.
		// int total_len = msg.getData_len();
		// ChannelBuffer sendBuff = ChannelBuffers.buffer(total_len);
		//
		// //包实体的长度(包的总长度-包头长度[4])
		// byte[] byte_len = Utils.intToBytes(total_len
		// - Constant.PACKET_HEADER_LEN, Constant.PACKETLEN_FIELD_LEN);
		// sendBuff.writeBytes(byte_len); //填写包长度
		// sendBuff.writeByte(msg.getPacket_no()); //填写包序号
		// sendBuff.writeBytes(msg.getData_buf()); //填写消息内容
		// return sendBuff;
		// return null;
	}
}
