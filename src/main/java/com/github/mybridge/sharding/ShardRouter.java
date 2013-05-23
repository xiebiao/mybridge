package com.github.mybridge.sharding;


/**
 * 分片路由
 * @author xiebiao
 */
public interface ShardRouter extends Router {

    public Shard getShard(ShardGroup shardGroup, long id) throws NotFoundShardException;
}
