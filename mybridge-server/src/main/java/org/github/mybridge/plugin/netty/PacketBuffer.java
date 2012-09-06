package org.github.mybridge.plugin.netty;

import java.io.UnsupportedEncodingException;

import mybridge2.packet.Packet;

import org.jboss.netty.buffer.ChannelBuffer;

import com.mysql.jdbc.Constants;

public class PacketBuffer extends Packet {
	static final int MAX_BYTES_TO_DUMP = 512;

	static final int NO_LENGTH_LIMIT = -1;

	static final long NULL_LENGTH = -1;

	int position;

	int bufferlength;

	ChannelBuffer buffer;

	private String charset = "utf-8";

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the bufferlength
	 */
	public int getBufferlength() {
		return bufferlength;
	}

	/**
	 * @param bufferlength
	 *            the bufferlength to set
	 */
	public void setBufferlength(int bufferlength) {
		this.bufferlength = bufferlength;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the buffer
	 */
	public ChannelBuffer getBuffer() {
		return buffer;
	}

	public PacketBuffer(ChannelBuffer buffer) {
		this.buffer = buffer;
		this.position = buffer.readerIndex();
		byte[] headerByte = new byte[3];
		// 读取包头信息
		buffer.getBytes(this.position, headerByte, 0, 3);
		this.bufferlength = headerByte[0];
	}

	public void setWritePosition(int position) {
		buffer.writerIndex(position);
	}

	public int getWritePosition() {
		return buffer.writerIndex();
	}

	// 获得授权包中加密的字节数组。
	public final byte[] getAuthencryptedPassword(long passwordLength) {
		int tmpInt = (int) passwordLength;
		int currentBufferByteIndex = this.getBufferByteIndex();
		byte[] bs = this.buffer.array();
		byte[] dest = new byte[tmpInt];
		System.arraycopy(bs, currentBufferByteIndex + 1, dest, 0, tmpInt);
		this.setBufferByteIndex(this.getBufferByteIndex() + 1 + tmpInt);
		return dest;
	}

	public final void writeByte(byte b) {
		ensureCapacity(1);
		buffer.writeByte(b);
	}

	public int writeBytes(byte[] ab) {
		return writeBytes(ab, 0, ab.length);
	}

	public int writeBytes(byte[] ab, int offset, int len) {
		ensureCapacity(len);
		buffer.writeBytes(ab, offset, len);
		return len;
	}

	public void writeNullTerminatedString(String s) {
		int len = s.length();
		ensureCapacity(len * 2 + 1);
		buffer.writeBytes(s.getBytes());
		buffer.writeByte(0);
	}

	/*
	 * 杈撳嚭瀛楃涓�
	 */
	public void writeStringNoNull(String s) {
		int len = s.length();
		ensureCapacity(len * 2);
		buffer.writeBytes(s.getBytes());
	}

	public void writeLong(long l) {
		ensureCapacity(8);
		buffer.writeLong(l);
	}

	public void writeInt(int i) {
		ensureCapacity(4);
		buffer.writeInt(i);
	}

	public void skipAuthFiller(int i) {
		this.setBufferByteIndex(this.getBufferByteIndex() + i);
		System.out.println(this.getBufferByteIndex());
	}

	public byte readByte() {
		this.setBufferByteIndex(this.getBufferByteIndex() + 1);
		// System.out.println(this.getBufferByteIndex());
		return buffer.readByte();
	}

	public short readShort() {
		return buffer.readShort();

	}

	public int readBytes(byte[] ab, int offset, int len) {
		buffer.readBytes(ab, offset, len);
		return len;
	}

	private byte[] getChannelBuffersAllBytes() {
		// buffer.toByteBuffer().array();
		byte[] bs = this.buffer.array();
		// this.buffer.getBytes(0, bs);
		return bs;
	}

	public int readInt() {
		this.setBufferByteIndex(this.getBufferByteIndex() + 4);
		// System.out.println(this.getBufferByteIndex());
		return this.buffer.readInt();

	}

	public final void writeFloat(float f) {
		ensureCapacity(4);
		int i = Float.floatToIntBits(f);
		buffer.writeInt(i);
		// byte[] b = buffer.array();
		// b[this.position++] = (byte) (i & 0xff);
		// b[this.position++] = (byte) (i >>> 8);
		// b[this.position++] = (byte) (i >>> 16);
		// b[this.position++] = (byte) (i >>> 24);
	}

	public final float readFloat() {
		return this.buffer.readFloat();
	}

	public byte[] readLenByteArray(int offset) {
		long len = this.readFieldLength();
		if (len == NULL_LENGTH) {
			return null;
		}
		if (len == 0) {
			return Constants.EMPTY_BYTE_ARRAY;
		}
		this.position += offset;
		byte[] bs = new byte[(int) len];
		this.buffer.readBytes(bs, 0, (int) len);
		return bs;
	}

	public byte[] getBytes(int offset, int len) {
		byte[] dest = new byte[len];
		System.arraycopy(this.buffer, offset, dest, 0, len);
		return dest;
	}

	public final void writeLengthCodedString(String s, String encoding) {
		if (s != null) {
			byte[] b;
			try {
				b = s.getBytes(encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				b = s.getBytes();
			}
			ensureCapacity(b.length + 9);
			writeFieldLength(b.length);
			writeBytesNoNull(b);
		} else {
			writeByte((byte) 0);
		}
	}

	//
	// Read a null-terminated string
	//
	// To avoid alloc'ing a new byte array, we
	// do this by hand, rather than calling getNullTerminatedBytes()
	//
	public final String readNullTerminatedString() {
		int currentByteIndex = this.getBufferByteIndex();
		// 该buffer中所有的数据。
		byte[] bs = this.buffer.array();
		int k = currentByteIndex;
		byte tmp = bs[currentByteIndex];
		while (tmp != 0) {
			k++;
			tmp = bs[k];
		}
		byte[] dst = new byte[k - currentByteIndex];
		System.arraycopy(bs, currentByteIndex, dst, 0, k - currentByteIndex);
		String s = new String(dst);
		k++;
		this.setBufferByteIndex(k);
		return s;
		// int i = this.buffer.readerIndex();
		// int len = 0;//tmp byte[]涓殑鎸囬拡銆�
		// int maxLen = bufferlength;
		// byte[] tmp = new byte[this.buffer.readableBytes()];
		// this.buffer.getBytes(i, tmp);
		// String string = new String(tmp);
		// int j = 0;//tmp byte[]涓殑鎸囬拡銆�
		// while ((i < maxLen) && (tmp[j] != 0))
		// {
		// j++;
		// len++;
		// i++;
		// }
		// byte[] bs = new byte[len];
		// System.arraycopy(tmp, j, bs, 0, len);
		// String s = new String(bs);
		// position += (len + 1);
		// buffer.readBytes(position);// update cursor
	}

	public final String readCommandpacketString() {
		int i = position;
		int len = 0;
		int maxLen = bufferlength;
		byte[] tmp = getChannelBuffersAllBytes();
		//String string = new String(tmp);
		while ((i < maxLen) && (tmp[i] != 0)) {
			len++;
			i++;
		}
		byte[] bs = new byte[len];
		System.arraycopy(tmp, i, bs, 0, len);
		String s = new String(bs);
		position += (len + 1);
		buffer.readBytes(position);// update cursor
		return s;
	}

	public final long readFieldLength() {
		int currentBufferByteIndex = this.getBufferByteIndex();
		byte[] bs = this.buffer.array();
		int j = bs[currentBufferByteIndex];
		// int i = this.buffer.getInt(this.buffer.readerIndex());
		switch (j) {
		case 251:
			return NULL_LENGTH;
		case 252:
			return this.buffer.readShort();
		case 253:
			return this.buffer.readInt();
		case 254:
			return this.buffer.readLong();
		default:
			return j;
		}
	}

	public final long readLong() {
		byte[] b = new byte[this.buffer.readableBytes()];
		this.buffer.getBytes(0, b);
		// int position = buffer.readerIndex();
		return ((long) b[this.position++] & 0xff)
				| (((long) b[this.position++] & 0xff) << 8)
				| ((long) (b[this.position++] & 0xff) << 16)
				| ((long) (b[this.position++] & 0xff) << 24);
	}

	public final int readLongInt() {
		byte[] b = new byte[this.buffer.readableBytes()];
		this.buffer.getBytes(0, b);
		return (b[this.position++] & 0xff) | ((b[this.position++] & 0xff) << 8)
				| ((b[this.position++] & 0xff) << 16);
	}

	public final long readLongLong() {
		byte[] b = new byte[this.buffer.readableBytes()];
		this.buffer.getBytes(0, b);
		return (b[this.position++] & 0xff)
				| ((long) (b[this.position++] & 0xff) << 8)
				| ((long) (b[this.position++] & 0xff) << 16)
				| ((long) (b[this.position++] & 0xff) << 24)
				| ((long) (b[this.position++] & 0xff) << 32)
				| ((long) (b[this.position++] & 0xff) << 40)
				| ((long) (b[this.position++] & 0xff) << 48)
				| ((long) (b[this.position++] & 0xff) << 56);
	}

	public final String readLengthCodedString(String encoding) {
		int fieldLength = (int) readFieldLength();
		byte[] bufferByte = new byte[this.buffer.readableBytes()];
		this.buffer.getBytes(0, bufferByte);
		if (fieldLength <= 0) {
			return null;
		}

		try {
			if (encoding != null) {
				return new String(bufferByte, position, fieldLength, encoding);
			} else {
				return new String(bufferByte, position, fieldLength);
			}
		} catch (UnsupportedEncodingException e) {
			return new String(bufferByte, position, fieldLength);
		} finally {
			position += fieldLength; // update cursor
		}
	}

	protected void ensureCapacity(int i) {
		buffer.ensureWritableBytes(i);
	}

	public final void writeFieldLength(long length) {
		if (length < 251) {
			writeByte((byte) length);
		} else if (length < 65536L) {
			ensureCapacity(3);
			writeByte((byte) 252);
			writeInt((int) length);
		} else if (length < 16777216L) {
			ensureCapacity(4);
			writeByte((byte) 253);
			writeLongInt((int) length);
		} else {
			ensureCapacity(9);
			writeByte((byte) 254);
			writeLongLong(length);
		}
	}

	public void writeLongInt(int value) {
		buffer.writeInt(value);
	}

	public void writeLongLong(long value) {
		buffer.writeLong(value);
	}

	public void writeBytesNoNull(byte[] bytes) {
		this.writeBytes(bytes);
	}

	public void writeShort(short s) {
		buffer.writeShort(s);
	}

	/**
	 * @param s
	 */
	public void writeMySQLInt(int s) {
		buffer.writeByte(s & 0xff);
		buffer.writeByte(s >>> 8);
	}

	/**
	 * @param s
	 */
	public void writeMySQLLong(long i) {
		buffer.writeByte((byte) (i & 0xff));
		buffer.writeByte((byte) (i >>> 8));
		buffer.writeByte((byte) (i >>> 16));
		buffer.writeByte((byte) (i >>> 24));
	}

	public void skip(int i) {
		// buffer.skipBytes(i);
		this.setBufferByteIndex(this.getBufferByteIndex() + i);
		// System.out.println(this.getBufferByteIndex());
		buffer.readBytes(i);
	}

	public void setReadPosition(int i) {
		buffer.readerIndex(i);
	}

	public int getReadPosition() {
		return buffer.readerIndex();
	}

	public boolean appendBufferToWrite(byte[] byts, boolean writeNow) {
		if (writeNow) {
			if (buffer.writerIndex() > 0) {
				buffer.writeBytes(byts);
				// conn.postMessage(buffer.toByteBuffer());
				// buffer.writeBytes(byts);
				buffer.clear();
			} else {
				buffer.writeBytes(byts);
				// conn.postMessage(byts);
			}
			return true;
		} else {
			buffer.writeBytes(byts);
			return true;
		}
	}

}