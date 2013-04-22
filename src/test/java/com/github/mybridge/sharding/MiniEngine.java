package com.github.mybridge.sharding;

import java.util.ArrayList;
import java.util.List;

import com.github.mybridge.engine.Engine;
import com.github.mybridge.engine.Host;
import com.github.mybridge.exception.LifecycleException;
import com.github.mybridge.sharding.impl.DefaultShardGroupRouter;
import com.github.mybridge.sharding.impl.DefaultTableRouter;
import com.github.mybridge.sharding.impl.FragmentTable;
import com.github.mybridge.sharding.impl.Shard;
import com.github.mybridge.sharding.impl.ShardGroup;

public class MiniEngine implements Engine {
	private List<ShardGroup> sgList;
	private ShardGroupRouter shardGroupRouter;
	private TableRouter tableRouter;

	public MiniEngine() {
		ShardGroup sg = new ShardGroup(0, "user", State.WRITE, 0, 10000);
		Host host0 = new Host("localhost:3306");
		Shard shard0 = new Shard(0, "shard", sg.getId(), host0);
		Host host1 = new Host("localhost:3307");
		Shard shard1 = new Shard(1, "shard", sg.getId(), host1);
		sg.addShard(shard0);
		sg.addShard(shard1);
		sgList = new ArrayList<ShardGroup>();
		sgList.add(sg);
	}

	@Override
	public void init() throws LifecycleException {
		shardGroupRouter = new DefaultShardGroupRouter();
		tableRouter = new DefaultTableRouter();
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

	private Shard getShard(String sql, String database) {
		// 解析SQL,得到id
		long id = 10;
		// 得到是写入sql
		State state = State.WRITE;
		ShardGroup sg = this.shardGroupRouter.getShardGroup(this.sgList, state,
				id);
		return sg.getShard(state, id);
	}

	@Override
	public FragmentTable getTable(String sql, String database) {
		// this.getShard(sql, database).getTable(id);
		return null;
	}
}
