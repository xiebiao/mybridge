package com.github.mybridge.sharding.impl;

import java.util.List;

import com.github.mybridge.sharding.ShardRouter;
import com.github.mybridge.sharding.State;

public class DefaultShardRouter extends AbstractRouter implements ShardRouter {

	public DefaultShardRouter() {

	}

	@Override
	public Shard getShard(ShardGroup shardGroup, State state, long id) {
		List<Shard> shards = shardGroup.getShards();
		int size = shards.size();
		for (int i = 0; i < size; i++) {
			Shard shard = shards.get(i);
			if (shard.getHashValue().equals(String.valueOf(mod(size, id)))) {
				return shard;
			}
		}
		return null;
	}

}
