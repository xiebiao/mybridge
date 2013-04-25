package com.github.mybridge.sharding.impl;

import java.util.List;
import java.util.Set;

import com.github.mybridge.sharding.ShardRouter;

public class DefaultShardRouter extends AbstractRouter implements ShardRouter {

	public DefaultShardRouter() {

	}

	@Override
	public Shard getShard(ShardGroup shardGroup, boolean canWrite, long id) {
		List<Shard> shards = shardGroup.getShards();
		int size = shards.size();
		for (int i = 0; i < size; i++) {
			Shard shard = shards.get(i);
			Set<String> hashs = shard.getHashValue();
			for (String hash : hashs) {
				if (hash.equals(String.valueOf(mod(size, id)))) {
					return shard;
				}
			}
		}
		return null;
	}

}
