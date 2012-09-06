package mybridge2.packet;

import java.io.UnsupportedEncodingException;

import org.github.mybridge.plugin.netty.PacketBuffer;

import mybridge2.util.Constants;

public class FieldPacket extends Packet {
	private String catalog = "def";

	private String db = "rds_db";

	private String table = "rds_table";

	private String orgTable = "rds_table";

	private String name;

	private String orgName;

	private int character = 33;// Charset: utf8 COLLATE utf8_general_ci (33)

	// 暂时定为40960
	private long length = 40960;

	private byte type = 0x0f;// FIELD_TYPE_VARCHAR

	private int flags = 0;

	private byte decimals = 0x00;

	private String definition;

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public byte getDecimals() {
		return decimals;
	}

	public void setDecimals(byte decimals) {
		this.decimals = decimals;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getDb() {
		return db;
	}

	public String getTable() {
		return table;
	}

	public String getOrgTable() {
		return orgTable;
	}

	public String getName() {
		return name;
	}

	public int getCharacter() {
		return character;
	}

	public byte getType() {
		return type;
	}

	public int getFlags() {
		return flags;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setOrgTable(String orgTable) {
		this.orgTable = orgTable;
	}

	public void setCharacter(short character) {
		this.character = character;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public void write2Buffer(PacketBuffer buffer)
			throws UnsupportedEncodingException {
		super.write2Buffer(buffer);
		buffer.writeLengthCodedString(this.catalog, Constants.ENCODING);
		buffer.writeLengthCodedString(this.db, Constants.ENCODING);
		buffer.writeLengthCodedString(this.table, Constants.ENCODING);
		buffer.writeLengthCodedString(this.orgTable, Constants.ENCODING);
		buffer.writeLengthCodedString(this.name, Constants.ENCODING);
		buffer.writeLengthCodedString(this.orgName, Constants.ENCODING);
		buffer.writeByte((byte) 12);
		buffer.writeMySQLInt(this.character);
		buffer.writeMySQLLong(this.length);
		buffer.writeByte(this.type);
		buffer.writeMySQLInt(this.flags);
		buffer.writeByte(this.decimals);
		buffer.writeMySQLInt(0);
		/**
		 * 暂时不支持default
		 */
		// buffer.writeLengthCodedString(this.definition, "Cp1252");
		super.afterPacketWritten(buffer);
	}

}