package mybridge2.packet;

import java.io.UnsupportedEncodingException;

import mybridge2.netty.PacketBuffer;

/**
 * <一句话功能简述> 所有packet的父类. <功能详细描述>
 * 
 * @author zKF36895
 * @version [版本号, 2011-6-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Packet {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	public int packetLength;

	public byte packetId;

	private int sPosition;

	/**
	 * 该Packet被调用初始化的时候byte中数组中的已读到的指针。
	 */
	private int bufferByteIndex;

	public int getBufferByteIndex() {
		return bufferByteIndex;
	}

	public void setBufferByteIndex(int bufferByteIndex) {
		this.bufferByteIndex = bufferByteIndex;
	}

	/**
	 * 读取包长度和包号码
	 * 
	 * @param buffer
	 */
	protected void init(PacketBuffer buffer) {
		buffer.setReadPosition(0);
		LOG.debug(buffer.getBuffer() + "");
		this.packetLength = ((buffer.readByte() & 0xFF)
				+ ((buffer.readByte() & 0xFF) << 8) + ((buffer.readByte() & 0xFF) << 16));
		this.packetId = buffer.readByte();
		// 指定buffer中数组的指针.
		this.bufferByteIndex = 4;
	}

	/*
	 * 输出包头占位的4个空字节
	 */
	public void write2Buffer(PacketBuffer buffer)
			throws UnsupportedEncodingException {
		this.sPosition = buffer.getWritePosition();
		buffer.writeBytes(new byte[4]);
	}

	/*
	 * 填充包头
	 */
	protected void afterPacketWritten(PacketBuffer buffer) {
		int position = buffer.getWritePosition();
		this.packetLength = (position - 4 - sPosition);
		buffer.setWritePosition(sPosition);
		buffer.writeByte((byte) (this.packetLength & 0xFF));
		buffer.writeByte((byte) (this.packetLength >>> 8));
		buffer.writeByte((byte) (this.packetLength >>> 16));
		buffer.writeByte((byte) (this.packetId & 0xFF));
		buffer.setWritePosition(position);
	}

}
