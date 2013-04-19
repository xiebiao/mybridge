package com.github.mybridge.engine;

import com.github.mybridge.Lifecycle;
import com.github.mybridge.sharding.Shard;

public interface Engine extends Lifecycle {

	public Shard getShard(String sql, String database);

}
