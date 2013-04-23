package com.github.mybridge.sharding;

import java.util.ArrayList;
import java.util.List;

import com.github.mybridge.engine.Engine;
import com.github.mybridge.engine.Address;
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
	
		return null;
	}

	@Override
	public FragmentTable getTable(String sql, String database) {
		// this.getShard(sql, database).getTable(id);
		return null;
	}
}
