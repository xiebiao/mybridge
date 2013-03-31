package com.github.mybridge.core;

import junit.framework.TestCase;

public class MySQLProtocolTest extends TestCase {
	public void test() {

		MySQLProtocol mp = new MySQLProtocol();
		mp.writeComplete();
	}
}
