package com.github.mybridge.sharding;

import java.util.List;

import com.github.mybridge.sharding.impl.DefaultShardGroupRouter;
import com.github.mybridge.sharding.impl.DefaultShardRouter;
import com.github.mybridge.sharding.impl.DefaultTableRouter;
import com.github.mybridge.sharding.impl.Shard;
import com.github.mybridge.sharding.impl.ShardGroup;
import com.github.mybridge.sharding.impl.ShardingRuleLoaderImpl;

public class RouterTest {
	private DefaultShardGroupRouter shardGroupRouter;
	private DefaultTableRouter tableRouter;
	private DefaultShardRouter shardRouter;
	private List<ShardGroup> sgList;
	private String sql;
	private String database;
	private boolean writable;
	private long id;
	private ShardingRuleLoader loader = new ShardingRuleLoaderImpl();

	public RouterTest(String sql, String database) {
		this.sql = sql;
		this.database = database;
		shardGroupRouter = new DefaultShardGroupRouter();
		tableRouter = new DefaultTableRouter();
		shardRouter = new DefaultShardRouter();

		// 解析sql
		this.writable = true;
		this.id = 5001;

		ShardGroup sg = this.shardGroupRouter.getShardGroup(this.loader.load(),
				writable, id);
		System.out.println("group:" + sg);
		Shard shard = this.shardRouter.getShard(sg, writable, id);
		System.out.println("shard:" + shard);
		System.out.println("table:" + this.tableRouter.getTable(shard, id));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "select * from user where id=100";
		String database = "user";
		RouterTest rt = new RouterTest(sql, database);

	}

}
