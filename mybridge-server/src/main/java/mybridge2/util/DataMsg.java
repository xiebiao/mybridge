package mybridge2.util;

public class DataMsg {
	/**
	 * data_len 数据的总共长度
	 */
	private int data_len;

	/**
	 * data_buf 消息的缓冲区
	 */
	private byte[] data_buf;

	/**
	 * commandID 发送的命令ID
	 */
	private byte commandID;

	/**
	 * packet_no 包编号
	 */
	private byte packet_no;

	/**
	 * msg_sn 消息的序号
	 */
	private int msg_sn;

	/**
	 * index 消息缓冲区已写到的位置索引。data_buf
	 */
	private int index;

	public DataMsg(int data_len) {
		super();
		this.data_len = data_len;
		this.index = 0;

		data_buf = new byte[data_len];
	}

	public int getData_len() {
		return data_len;
	}

	public void setData_len(int data_len) {
		this.data_len = data_len;
	}

	public byte[] getData_buf() {
		return data_buf;
	}

	public boolean setData_buf(byte[] data_buf) {
		if (data_buf.length > this.data_len) {
			return false;
		}
		this.data_buf = data_buf;
		return true;
	}

	/**
	 * <一句话功能简述> 设置缓冲区 <功能详细描述>
	 * 
	 * @param index
	 *            缓冲区中的当前写位置
	 * @param data_buf
	 *            待写的内容
	 * @see [类、类#方法、类#成员]
	 */
	public boolean setData_buf(int index, byte[] data_buf) {
		if (index >= this.data_len
				|| data_buf.length > this.data_len - this.index) {
			return false;
		}
		for (int i = 0; i < data_buf.length; i++) {
			this.data_buf[index++] = data_buf[i];
		}
		return true;
	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处继续写入
	 * 
	 * @param data_buf
	 * @see [类、类#方法、类#成员]
	 */
	public boolean writeByte(byte data_buf) {
		if (this.data_len <= this.index) {
			// 缓冲区不够用,表示信息写入缓冲区失败
			return false;
		}

		this.data_buf[this.index++] = data_buf;

		return true;

	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处继续写入
	 * 
	 * @param data_buf
	 * @see [类、类#方法、类#成员]
	 */
	public boolean writeBytes(byte[] data_buf) {
		if (data_buf.length > this.data_len - this.index) {
			// 缓冲区不够用
			return false;
		}
		for (int i = 0; i < data_buf.length; i++) {
			this.data_buf[this.index++] = data_buf[i];
		}

		return true;

	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处继续写入
	 * 
	 * @param int
	 * @see [类、类#方法、类#成员]
	 */
	public boolean writeInt(int arg) {
		if (this.data_len - this.index < 4) {
			// 缓冲区不够用
			return false;
		}
		for (int i = 4; i >= 4; i--) {
			this.data_buf[this.index++] = (byte) (arg >> 8 * i & 0xff);
		}

		return true;

	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处继续写入
	 * 
	 * @param int
	 * @see [类、类#方法、类#成员]
	 */
	public boolean writeLong(long arg) {
		if (this.data_len - this.index < 8) {
			// 缓冲区不够用
			return false;
		}
		for (int i = 7; i >= 0; i--) {
			this.data_buf[this.index++] = (byte) (arg >> 8 * i & 0xff);
		}

		return true;

	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处继续写入
	 * 
	 * @param short
	 * @see [类、类#方法、类#成员]
	 */
	public boolean writeShort(short arg) {
		if (this.data_len - this.index < 2) {
			// 缓冲区不够用
			return false;
		}
		for (int i = 0; i < 2; i++) {
			this.data_buf[this.index++] = (byte) (arg << 8 * i & 0xff);
		}

		return true;

	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处开始读出
	 * 
	 * @param data_buf
	 * @see [类、类#方法、类#成员]
	 */
	public byte readByte() {
		return this.data_buf[this.index++];
	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处开始读出
	 * 
	 * @param data_buf
	 * @see [类、类#方法、类#成员]
	 */
	public boolean readBytes(byte[] data_buf) {
		if (data_buf.length > this.data_len - this.index) {
			// 缓冲区不够用
			return false;
		}
		for (int i = 0; i < data_buf.length; i++) {
			data_buf[i] = this.data_buf[this.index++];
		}

		return true;

	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处开始读出
	 * 
	 * @param int
	 * @see [类、类#方法、类#成员]
	 */
	public int readInt() {
		int result = 0;
		if (this.data_len - this.index >= 4) {

			for (int i = 0; i < 4; i++) {
				result = result << (8 * i) | this.data_buf[this.index++];
			}
		}

		return result;

	}

	/**
	 * <一句话功能简述> <功能详细描述> 在index处开始读出
	 * 
	 * @param short
	 * @see [类、类#方法、类#成员]
	 */
	public short readShort() {
		short result = 0;
		if (this.data_len - this.index >= 2) {

			for (int i = 0; i < 2; i++) {
				result = (short) (result << (8 * i) | this.data_buf[this.index++]);
			}
		}

		return result;

	}

	public byte getCommandID() {
		return commandID;
	}

	public void setCommandID(byte commandID) {
		this.commandID = commandID;
	}

	public byte getPacket_no() {
		return packet_no;
	}

	public void setPacket_no(byte packet_no) {
		this.packet_no = packet_no;
	}

	/**
	 * @return 返回 msg_sn
	 */
	public int getMsg_sn() {
		return msg_sn;
	}

	/**
	 * @param 对msg_sn进行赋值
	 */
	public void setMsg_sn(int msg_sn) {
		this.msg_sn = msg_sn;
	}

	/**
	 * @return 返回 index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param 对index进行赋值
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
