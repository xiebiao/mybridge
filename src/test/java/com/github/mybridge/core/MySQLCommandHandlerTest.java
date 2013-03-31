package com.github.mybridge.core;

import java.util.List;

import junit.framework.TestCase;

import com.github.mybridge.core.packet.CommandPacket;
import com.github.mybridge.core.packet.Packet;

public class MySQLCommandHandlerTest extends TestCase {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MySQLCommandHandlerTest.class);

	public void test() {
		MySQLCommandHandler mh = new MySQLCommandHandler();
		CommandPacket packet = new CommandPacket();
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
		} catch (CommandExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}