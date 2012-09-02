package mybridge2.packet;

import java.io.UnsupportedEncodingException;

import mybridge2.netty.PacketBuffer;

public abstract class CommandPacket extends Packet {
	public static final byte COM_SLEEP = 0;

	public static final byte COM_QUIT = 1;

	public static final byte COM_INIT_DB = 2;

	public static final byte COM_QUERY = 3;

	public static final byte COM_FIELD_LIST = 4;

	public static final byte COM_CREATE_DB = 5;

	public static final byte COM_DROP_DB = 6;

	public static final byte COM_REFRESH = 7;

	public static final byte COM_SHUTDOWN = 8;

	public static final byte COM_STATISTICS = 9;

	public static final byte COM_PROCESS_INFO = 10;

	public static final byte COM_CONNECT = 11;

	public static final byte COM_PROCESS_KILL = 12;

	public static final byte COM_DEBUG = 13;

	public static final byte COM_PING = 14;

	public static final byte COM_TIME = 15;

	public static final byte COM_DELAYED_INSERT = 16;

	public static final byte COM_CHANGE_USER = 17;

	public static final byte COM_BINLOG_DUMP = 18;

	public static final byte COM_TABLE_DUMP = 19;

	public static final byte COM_CONNECT_OUT = 20;

	public static final byte COM_REGISTER_SLAVE = 21;

	public static final byte COM_STMT_PREPARE = 22;

	public static final byte COM_STMT_EXECUTE = 23;

	public static final byte COM_STMT_SEND_LONG_DATA = 24;

	public static final byte COM_STMT_CLOSE = 25;

	public static final byte COM_STMT_RESET = 26;

	public static final byte COM_SET_OPTION = 27;

	public static final byte COM_STMT_FETCH = 28;

	public static final byte COM_EOF = -2;

	private byte command;

	public byte getCommand() {
		return command;
	}

	public void setCommand(byte command) {
		this.command = command;
	}

	public void init(PacketBuffer buffer) {
		super.init(buffer);

		this.command = buffer.readByte();
	}

	public void write2Buffer(PacketBuffer buffer)
			throws UnsupportedEncodingException {
		super.write2Buffer(buffer);
		buffer.writeByte(this.command);
	}

	public String toString() {
		// StringBuilder s = new StringBuilder();
		// s.append("[Length=").append(StringFillFormat.format(this.packetLength,
		// 4));
		// s.append(", PacketId=").append(StringFillFormat.format(this.packetId,
		// 2));
		// s.append(", Command=").append(StringFillFormat.format(this.command,
		// 2)).append("]");
		// return s.toString();
		return null;
	}
}