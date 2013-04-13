package com.github.mybridge.core;

import junit.framework.TestCase;

import com.github.mybridge.netty.NettyMySQLProtocolImpl;

public class MySQLProtocolTest extends TestCase {
	public void test() {

		NettyMySQLProtocolImpl mp = new NettyMySQLProtocolImpl();
		mp.writeCompleted();
	}
}
