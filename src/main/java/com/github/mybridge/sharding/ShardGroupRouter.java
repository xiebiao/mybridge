package com.github.mybridge.sharding;

import java.util.List;


/**
 * 分组路由
 * @author xiebiao
 */
public interface ShardGroupRouter extends Router {

    public ShardGroup getShardGroup(List<ShardGroup> shardGroup, boolean writable, long id)
            throws NotFoundShardGroupException;
}
