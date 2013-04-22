package com.github.mybridge.sharding;

import java.util.List;

import com.github.mybridge.sharding.impl.ShardGroup;

public interface ShardGroupRouter extends Router {
	public ShardGroup getShardGroup(List<ShardGroup> shardGroup, State state,
			long id);
}
