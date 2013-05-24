package com.github.mybridge.core;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.mybridge.core.packet.AbstractPacket;
import com.github.mybridge.core.packet.EofPacket;
import com.github.mybridge.core.packet.FieldDescriptionPacket;
import com.github.mybridge.core.packet.OkPacket;
import com.github.mybridge.core.packet.Packet;
import com.github.mybridge.core.packet.ResultSetPacket;
import com.github.mybridge.core.packet.RowDataPacket;

/**
 * just for test
 * 
 * @author xiebiao
 * 
 */
public class MockHandler implements MySQLHandler {

	@Override
	public List<Packet> execute(Packet cmd) throws ExecuteException {
		int columnCount = 3;
		int dataCount = 2;
		List<Packet> packetList = new ArrayList<Packet>();
		OkPacket ok = new OkPacket();
		packetList.add(ok);
		ResultSetPacket resultPacket = new ResultSetPacket(columnCount);
		packetList.add(resultPacket);
		for (int i = 1; i <= columnCount; i++) {
			FieldDescriptionPacket fieldPacket = new FieldDescriptionPacket();
			fieldPacket.setDatabase("wp");
			fieldPacket.setTable("users");
			fieldPacket.setOrgTable("users");
			fieldPacket.setName("name");
			fieldPacket.setOrgName("name");
			fieldPacket.setType((byte) MySQLCommand
					.javaTypeToMysql(Types.CHAR));
			fieldPacket.setLength(5);
			packetList.add(fieldPacket);
		}
		while (dataCount-- > 0) {
			RowDataPacket rowPacket = new RowDataPacket("utf8");
			for (int i = 1; i <= columnCount; i++) {
				String value = "test:" + i;
				rowPacket.addValue(value);
			}
			packetList.add(rowPacket);
		}
		packetList.add(new EofPacket());
		return packetList;
	}

	@Override
	public void setCharset(String charset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDatabase(String database) {
		// TODO Auto-generated method stub

	}

}
