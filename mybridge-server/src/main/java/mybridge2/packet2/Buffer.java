package mybridge2.packet2;

import java.io.UnsupportedEncodingException;

public class Buffer {
	Packer packer = new Packer(false);
	byte[] buf;
	int pos = 0;

	public Buffer(int initLen) {
		buf = new byte[initLen];
	}

	public Buffer(byte[] buf) {
		this.buf = buf;
	}

	public void writeBytes(byte[] bs) {
		System.arraycopy(bs, 0, buf, pos, bs.length);
		pos += bs.length;
	}

	public void writeUInt32(long num) {
		byte[] tmp = packer.packUInt32(num);
		System.arraycopy(tmp, 0, buf, pos, tmp.length);
		pos += tmp.length;
	}

	public void writeUInt16(int num) {
		byte[] tmp = packer.packUInt16(num);
		System.arraycopy(tmp, 0, buf, pos, tmp.length);
		pos += tmp.length;
	}

	public void writeInt64(long num) {
		byte[] tmp = packer.packInt64(num);
		System.arraycopy(tmp, 0, buf, pos, tmp.length);
		pos += tmp.length;
	}

	public void writeByte(byte num) {
		buf[pos++] = num;
	}

	public void writeNullString(String str) {
		byte[] tmp = str.getBytes();
		System.arraycopy(tmp, 0, buf, pos, tmp.length);
		pos += tmp.length;
	}

	public void writeRemainString(String str) {
		byte[] tmp = str.getBytes();
		System.arraycopy(tmp, 0, buf, pos, tmp.length);
		pos += tmp.length;
	}

	public void writeLCString(String str) {
		if (str == null) {
			writeByte((byte) 251);
			return;
		}
		byte[] tmp = str.getBytes();
		long len = tmp.length;
		writeLCB(len);
		writeBytes(tmp);
	}

	public void writeLCString(String str, String charset) {
		if (str == null) {
			writeByte((byte) 251);
			return;
		}
		byte[] tmp;
		try {
			tmp = str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			tmp = new byte[0];
		}
		long len = tmp.length;
		writeLCB(len);
		writeBytes(tmp);
	}

	public void writeLCB(long num) {
		if (num <= 250) {
			writeByte((byte) (num & 0xff));
			return;
		}

		if (num <= 0xffff) {
			writeByte((byte) 252);
			writeUInt16((int) num);
			return;
		}

		if (num <= 0xffffff) {
			writeByte((byte) 253);
			writeByte((byte) ((num >> 16) & 0xff));
			writeByte((byte) ((num >> 8) & 0xff));
			writeByte((byte) ((num) & 0xff));
			return;
		}

		writeByte((byte) 254);
		writeInt64((int) num);
	}

	public byte[] getBytes() {
		return buf;
	}

	public long readInt64() {
		byte[] tmp = new byte[8];
		System.arraycopy(buf, pos, tmp, 0, tmp.length);
		pos += tmp.length;
		return packer.unpackInt64(tmp);
	}

	public long readUInt32() {
		byte[] tmp = new byte[4];
		System.arraycopy(buf, pos, tmp, 0, tmp.length);
		pos += tmp.length;
		return packer.unpackUInt32(tmp);
	}

	public int readUInt16() {
		byte[] tmp = new byte[2];
		System.arraycopy(buf, pos, tmp, 0, tmp.length);
		pos += tmp.length;
		return packer.unpackUInt16(tmp);
	}

	public byte readByte() {
		return buf[pos++];
	}

	public byte[] readBytes(int len) {
		byte[] tmp = new byte[len];
		System.arraycopy(buf, pos, tmp, 0, tmp.length);
		pos += tmp.length;
		return tmp;
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

	public String readLCString() {
		long len = readLCB();
		byte[] tmp = readBytes((int) len);
		return new String(tmp);
	}
	public byte[] readLCBytes() {
		long len = readLCB();
		byte[] tmp = readBytes((int) len);
		return tmp;
	}

	public String readRemainString() {
		int len = buf.length - pos;
		byte[] tmp = readBytes(len);
		return new String(tmp);
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
			return ((readByte() & 0xff) >> 16) | ((readByte() & 0xff) >> 8) | ((readByte() & 0xff));
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

	public String toString() {
		return StringUtil.dumpAsHex(buf, buf.length);
	}
}
