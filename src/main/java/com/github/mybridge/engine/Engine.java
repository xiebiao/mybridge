package com.github.mybridge.engine;

import com.github.mybridge.Lifecycle;
import com.github.mybridge.sharding.impl.FragmentTable;
import com.github.mybridge.sharding.impl.Shard;

public interface Engine extends Lifecycle {

	//public Shard getShard(String sql, String database);

	//public List<ShardGroup> getShardGroup();

	public FragmentTable getTable(String sql, String database);
}
