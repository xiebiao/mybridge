package com.github.mybridge.sharding;

import java.util.List;

import com.github.mybridge.engine.Engine;
import com.github.mybridge.engine.Host;
import com.github.mybridge.exception.LifecycleException;
import com.github.mybridge.sharding.impl.Shard;
import com.github.mybridge.sharding.impl.ShardGroup;

public class MiniEngine implements Engine {

	@Override
	public void init() throws LifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws LifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() throws LifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public Shard getShard(String sql, String database) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShardGroup> getShardGroup() {
		ShardGroup sg = new ShardGroup(0, "user", State.WRITE, 0, 10000);
		Host host0 = new Host("localhost:3306");
		Shard shard0 = new Shard(0, "shard", sg.getId(), host0);
		Host host1 = new Host("localhost:3307");
		Shard shard1 = new Shard(1, "shard", sg.getId(), host1);
		//sg.addShard(null)
		return null;
	}
}
