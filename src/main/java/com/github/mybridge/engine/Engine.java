package com.github.mybridge.engine;

import java.util.List;

import com.github.mybridge.Lifecycle;
import com.github.mybridge.sharding.impl.Shard;
import com.github.mybridge.sharding.impl.ShardGroup;

public interface Engine extends Lifecycle {

	public Shard getShard(String sql, String database);

	public List<ShardGroup> getShardGroup();

}
