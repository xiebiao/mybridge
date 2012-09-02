package mybridge2.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class DataDecoder extends FrameDecoder {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1,
			ChannelBuffer channelbuffer) throws Exception {

		LOG.debug("DataDecoder decode");
		if (channelbuffer.readableBytes() < 5) {
			return null;
		} else {
			PacketBuffer buffer = new PacketBuffer(channelbuffer);
			int tmp = channelbuffer.readableBytes();
			channelbuffer.readerIndex(tmp);
			// channelbuffer.resetReaderIndex();
			// channelbuffer.resetWriterIndex();
			return buffer;
		}

		// /**
		// * 所有消息的通用消息头格式
		// *
		// * field_name len content
		// * =================================
		// * packet_len 3 数据包长度，不包含前4个字节，固定使用大端格式
		// * packet_no 1 请求命令固定为0,响应命令固定为1
		// * pckage_buffer n 消息所有的内容
		// */
		//
		// //此处解码根据通信协议定义的各种包类型进行分别处理
		// if (channelbuffer.readableBytes() < Constant.PACKET_HEADER_LEN)
		// {
		// return null;
		// }
		//
		// //getxxx与readxxx的区别是，前者不移动readerIndex，而后者会移动
		// //因此，在未确保数据包完整之前，先不移动readerIndex
		//
		// //读取三个字节，表示packet_len
		// byte[] packet_len = new byte[3];
		// channelbuffer.getBytes(channelbuffer.readerIndex(), packet_len);
		//
		// //再读取一字节，表示packet_no
		// byte packet_no = channelbuffer.getByte(channelbuffer.readerIndex() +
		// 3);
		//
		// int packageLen = Utils.toInt(packet_len, 0, 3);
		//
		// //整个数据包的长度(package header + package)
		// if (channelbuffer.readableBytes() < packageLen +
		// Constant.PACKET_HEADER_LEN)
		// {
		// //本次接收的数据包长度不足
		// return null;
		// }
		// //忽略掉消息头
		// channelbuffer.readerIndex(Constant.PACKET_HEADER_LEN);
		//
		// //接收正常的数据包
		// DataMsg msg = new DataMsg(packageLen + Constant.PACKET_HEADER_LEN);
		// channelbuffer.readBytes(msg.getData_buf());
		// //获取数据包的packet_no、MSG_ID及sn
		// msg.setPacket_no(packet_no);
		//
		// return msg;
	}

}
