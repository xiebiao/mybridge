package com.github.mybridge.engine;

import java.util.HashMap;
import java.util.Map;

import com.github.mybridge.exception.LifecycleException;
import com.github.mybridge.sharding.Partition;
import com.github.mybridge.sharding.Shard;

public class DefaultEngine implements Engine {
	private static Map<String, Shard> shards = new HashMap<String, Shard>();
	private static Map<String, Partition> partitions = new HashMap<String, Partition>();
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(DefaultEngine.class);

	@Override
	public Shard getServer(String sql, String database) {
		Shard shard = shards.get(sql);
		if (null != shard) {
			return shard;
		}
		Parser parser = new SqlParser(this);
		// 解析SQL得到对应的Shard
		return null;
		// return servers.get(database);
	}

	@Override
	public void init() throws LifecycleException {
		// 初始化分区
		logger.debug("init partitions");
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
