package com.github.mybridge.jnet;

import com.github.jnet.Configuration;
import com.github.jnet.Server;

public class MybridgeServer extends Server<MysqlSession> {

	public MybridgeServer(Configuration config,
			Class<MysqlSession> sessionHandler) {
		super(config, sessionHandler);
	}

}
