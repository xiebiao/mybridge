package mybridge2.packet2;
 

public abstract class Packet {

	public abstract void putBytes(byte[] bs);

	public abstract byte[] getBytes();

	public String toString() {
		if (getBytes() == null) {
			return "";
		}
		return StringUtil.dumpAsHex(getBytes(), getBytes().length);
	}

 
}
