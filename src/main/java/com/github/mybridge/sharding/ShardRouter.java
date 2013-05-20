package com.github.mybridge.sharding;

import com.github.mybridge.sharding.support.Shard;
import com.github.mybridge.sharding.support.ShardGroup;

/**
 * 分片路由
 * @author xiebiao
 */
public interface ShardRouter extends Router {

    public Shard getShard(ShardGroup shardGroup, long id);
}
