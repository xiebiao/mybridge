package com.github.mybridge.sharding;

import com.github.mybridge.sharding.impl.Shard;
import com.github.mybridge.sharding.impl.ShardGroup;

/**
 * 分片路由
 * 
 * @author xiebiao
 */
public interface ShardRouter extends Router {

	public Shard getShard(ShardGroup shardGroup, boolean canWrite, long id);
}
