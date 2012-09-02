package org.github.mybridge.core.buffer;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;

public class ByteBuffer extends PackerBuffer {

	int pos = 0;
	byte[] buf;

	public ByteBuffer() {
	}

	public ByteBuffer(int capacity) {
		buf = new byte[capacity];
		this.bigEndian = false;
	}

	public ByteBuffer(byte[] temp) {
		this.buf = temp;
		this.bigEndian = false;
	}

	public byte[] getBytes() {
		return buf;
	}

	public void putByte(byte bytes) {
		buf[pos++] = bytes;
	}

	public void putBytes(byte[] bytes) {
		System.arraycopy(bytes, 0, buf, pos, bytes.length);
		pos += bytes.length;
	}

	public void putUInt32(long num) {
		byte[] temp = packUInt32(num);
		System.arraycopy(temp, 0, buf, pos, temp.length);
		pos += temp.length;
	}

	public void putUInt16(int num) {
		byte[] temp = packUInt16(num);
		System.arraycopy(temp, 0, buf, pos, temp.length);
		pos += temp.length;
	}

	public void putInt64(long num) {
		byte[] temp = packInt64(num);
		System.arraycopy(temp, 0, buf, pos, temp.length);
		pos += temp.length;
	}

	public void putNullString(String str) {
		byte[] temp = str.getBytes();
		System.arraycopy(temp, 0, buf, pos, temp.length);
		pos += temp.length;
	}

	public void putRemainString(String str) {
		byte[] temp = str.getBytes();
		System.arraycopy(temp, 0, buf, pos, temp.length);
		pos += temp.length;
	}

	public byte[] readBytes(int len) {
		byte[] tmp = new byte[len];
		System.arraycopy(buf, pos, tmp, 0, tmp.length);
		pos += tmp.length;
		return tmp;
	}

	public byte readByte() {
		return buf[pos++];
	}

	public long readInt64() {
		byte[] temp = new byte[8];
		System.arraycopy(buf, pos, temp, 0, temp.length);
		pos += temp.length;
		return unpackInt64(temp);
	}

	public long readUInt32() {
		byte[] temp = new byte[4];
		System.arraycopy(buf, pos, temp, 0, temp.length);
		pos += temp.length;
		return unpackUInt32(temp);
	}

	public long readUInt16() {
		byte[] temp = new byte[2];
		System.arraycopy(buf, pos, temp, 0, temp.length);
		pos += temp.length;
		return unpackUInt16(temp);
	}

	public String readNullString() {
		int i = 0;
		int len = 0;
		for (i = pos; i < buf.length; i++) {
			len++;
			if (buf[i] == 0) {
				break;
			}
		}

		if (len == 0) {
			return "";
		}

		byte[] tmp = new byte[len];
		System.arraycopy(buf, pos, tmp, 0, tmp.length);
		pos += tmp.length;
		return new String(tmp);
	}

	public byte[] readLCBytes() {
		long len = readLCB();
		byte[] tmp = readBytes((int) len);
		return tmp;
	}

	public String readRemainString() {
		int len = buf.length - pos;
		byte[] temp = readBytes(len);
		return new String(temp);
	}

	public byte[] readRemainBytes() {
		int len = buf.length - pos;
		return readBytes(len);
	}

	public long readLCB() {
		int first = readByte() & 0xff;
		if (first <= 250) {
			return first;
		}

		if (first == 251) {
			return 0;
		}

		if (first == 252) {
			return readUInt16();
		}

		if (first == 253) {
			return ((readByte() & 0xff) >> 16) | ((readByte() & 0xff) >> 8)
					| ((readByte() & 0xff));
		}

		if (first == 254) {
			return readInt64();
		}

		return 0;
	}

	public static int getLCBLen(long num) {
		if (num <= 250) {
			return 1;
		}
		if (num <= 0xffff) {
			return 3;
		}
		if (num <= 0xffffff) {
			return 4;
		}
		return 9;
	}

	public void putLCB(long num) {
		if (num <= 250) {
			putByte((byte) (num & 0xff));
			return;
		}
		if (num <= 0xffff) {
			putByte((byte) 252);
			putUInt16((int) num);
			return;
		}
		if (num <= 0xffffff) {
			putByte((byte) 253);
			putByte((byte) ((num >> 16) & 0xff));
			putByte((byte) ((num >> 8) & 0xff));
			putByte((byte) ((num) & 0xff));
			return;
		}
		putByte((byte) 254);
		putInt64((int) num);
	}

	public void putLCString(String str) {
		if (str == null) {
			putByte((byte) 251);
			return;
		}
		byte[] tmp = str.getBytes();
		long len = tmp.length;
		putLCB(len);
		putBytes(tmp);
	}

	public void putLCString(String str, String charset) {
		if (str == null) {
			putByte((byte) 251);
			return;
		}
		byte[] tmp;
		try {
			tmp = str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			tmp = new byte[0];
		}
		long len = tmp.length;
		putLCB(len);
		putBytes(tmp);
	}

	public static int getLCStringLen(String str) {
		if (str == null) {
			return 1;
		}
		int valueLen = str.getBytes().length;
		int len = getLCBLen(valueLen);
		return len + valueLen;
	}

	public static int getLCStringLen(String str, String charset) {
		if (str == null) {
			return 1;
		}
		int valueLen;
		try {
			valueLen = str.getBytes(charset).length;
		} catch (UnsupportedEncodingException e) {
			return 1;
		}
		int len = getLCBLen(valueLen);
		return len + valueLen;
	}

	public static void main(String[] args) {
		IoBuffer buffer = IoBuffer.allocate(4);
		buffer.setAutoExpand(true);
		byte[] temp = new byte[] { 3, 2, 5, 6 };
		buffer.put(temp);
		byte by = 33;
		buffer.put(by);
		byte[] arr = new byte[] { 8, 9, 10, 11 };
		buffer.put(arr);

		byte[] buArr = buffer.array();
		for (byte b : buArr) {
			System.out.println(b & 0xff);
		}

	}

}
