package com.github.mybridge.jnet;

import com.github.jnet.Configuration;
import com.github.jnet.Server;

public class MyBridgeServer extends Server<MysqlSession> {

	public MyBridgeServer(Configuration config,
			Class<MysqlSession> sessionHandler) {
		super(config, sessionHandler);
	}

}
