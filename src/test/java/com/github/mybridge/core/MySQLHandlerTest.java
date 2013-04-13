package com.github.mybridge.core;

import java.util.List;

import junit.framework.TestCase;

import com.github.mybridge.core.packet.CommandsPacket;
import com.github.mybridge.core.packet.Packet;

public class MySQLHandlerTest extends TestCase {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLHandlerTest.class);

	public void test() {
		MySQLHandler mh = new MySQLHandler();
		CommandsPacket packet = new CommandsPacket();
		packet.setType((byte)0x03);
		packet.putBytes("select * from wp_users".getBytes());
		try {
			List<Packet> packets = mh.execute(packet);
			if (packets == null || packets.size() == 0) {
				logger.debug("返回空");
			}
			for (Packet p : packets) {
				System.out.println(new String(p.getBytes()));
			}
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
