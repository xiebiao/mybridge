package com.github.mybridge.core;

import junit.framework.TestCase;

import com.github.mybridge.netty.MySQLProtocol;

public class MySQLProtocolTest extends TestCase {
	public void test() {

		MySQLProtocol mp = new MySQLProtocol();
		mp.writeCompleted();
	}
}
