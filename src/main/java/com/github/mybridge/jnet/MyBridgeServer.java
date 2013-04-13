package com.github.mybridge.jnet;

import com.github.jnet.Configuration;
import com.github.jnet.Server;

public class MyBridgeServer extends Server<MySQLSession> {

	public MyBridgeServer(Configuration config,
			Class<MySQLSession> sessionHandler) {
		super(config, sessionHandler);
	}

}
