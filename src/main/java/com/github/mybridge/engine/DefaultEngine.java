package com.github.mybridge.engine;

import java.util.HashMap;
import java.util.Map;

import com.github.mybridge.exception.LifecycleException;

public class DefaultEngine implements Engine {
	private static final String config = "mybridge.xml";
	/** servers */
	private static final Map<String, DatabaseServer> servers = new HashMap<String, DatabaseServer>();

	@Override
	public DatabaseServer getServer(String database) {
		return servers.get(database);
	}

	@Override
	public void init() throws LifecycleException {
		// 加载配置文件 mybridge.xml

	}

	@Override
	public void start() throws LifecycleException {

	}

	@Override
	public void stop() throws LifecycleException {

	}

	@Override
	public void destroy() throws LifecycleException {

	}

}
