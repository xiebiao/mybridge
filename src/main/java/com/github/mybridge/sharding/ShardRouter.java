package com.github.mybridge.sharding;

import com.github.mybridge.sharding.impl.Shard;
import com.github.mybridge.sharding.impl.ShardGroup;

/**
 * 分片,分表路由
 * 
 * @author xiebiao
 */
public interface ShardRouter extends Router {

	public Shard getShard(ShardGroup shardGroup, State state, long id);
}
