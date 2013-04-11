package com.github.mybridge.jnet;

import com.github.jnet.Configuration;
import com.github.jnet.Server;
import com.github.mybridge.Mybridge;

public class MybridgeServer extends Server<MysqlSession> {
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(Mybridge.class);

	public MybridgeServer(Configuration config,
			Class<MysqlSession> sessionHandler) {
		super(config, sessionHandler);
		LOG.debug("MybridgeServer start");
	}

}
