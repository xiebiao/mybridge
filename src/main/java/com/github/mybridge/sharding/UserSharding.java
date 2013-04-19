package com.github.mybridge.sharding;

import java.util.ArrayList;
import java.util.List;

import com.github.mybridge.engine.Host;
import com.github.mybridge.engine.JDBCProperties;

/**
 * 模拟按用户信息业务分区,用户信息中有两张表:user,profile
 * 
 * @author xiebiao[谢彪]
 * @email xiebiao@jd.com
 */
public class UserSharding {
	private String business = "user";
	private String table0 = "user";
	private String table1 = "profile";
	private Partition partition;

	public UserSharding() {
		partition = new Partition();
		partition.setId(0);
		partition.setName(business);
		int max = 10000;
		// 分组(1),容量(0-10000)
		List<ShardGroup> shardGroups = new ArrayList<ShardGroup>();
		ShardGroup shardGroup = new ShardGroup();
		shardGroup.setId(0);
		shardGroup.setName("UserGroup");
		shardGroup.setStartId(0);
		shardGroup.setEndId(max);
		// 分片(2)
		List<Shard> shards = new ArrayList<Shard>();
		Host host = new Host("10.28.168.53:3306");
		Shard shard = new Shard(0, business + "_shard", shardGroup.getId(),
				host);
		shard.setHashValue("0");
		// 分表(2)
		List<FragmentTable> tables = new ArrayList<FragmentTable>();
		FragmentTable t0 = new FragmentTable(0, table0, shard.getId(), 0, 5000);
		FragmentTable t1 = new FragmentTable(1, table0, shard.getId(), 5000,
				max);
		tables.add(t0);
		tables.add(t1);
		shard.setTables(tables);
		shards.add(shard);
		shardGroup.setShards(shards);
		shardGroups.add(shardGroup);
		partition.setShardGroups(shardGroups);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserSharding us = new UserSharding();
		System.out.println(us.partition.toString());
		System.out.println(JDBCProperties.getPropertiesString());
		
	}

}
