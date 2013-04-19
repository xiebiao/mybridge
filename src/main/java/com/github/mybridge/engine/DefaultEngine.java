package com.github.mybridge.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mybridge.exception.LifecycleException;
import com.github.mybridge.sharding.Shard;
import com.github.mybridge.sharding.ShardGroup;

public class DefaultEngine implements Engine {
	private static Map<String, Shard> shards = new HashMap<String, Shard>();
	private List<ShardGroup> shardGroups;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(DefaultEngine.class);

	@Override
	public Shard getShard(String sql, String database) {
		Shard shard = shards.get(sql);
		if (null != shard) {
			return shard;
		}

		// 解析SQL得到对应的Shard
		return null;
		// return servers.get(database);
	}

	@Override
	public void init() throws LifecycleException {
		// 加载分组shardGroups
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
