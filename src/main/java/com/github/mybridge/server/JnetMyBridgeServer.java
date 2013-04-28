package com.github.mybridge.server;

import com.github.jnet.Server;

public class JnetMyBridgeServer extends Server<JnetMySQLSession> {

	public JnetMyBridgeServer(String name) {
		super(name);
	}

}
