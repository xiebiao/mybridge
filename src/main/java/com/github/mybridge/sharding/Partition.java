package com.github.mybridge.sharding;

import java.util.List;

/**
 * 一个分区内有多个分组
 * 
 * @author xiebiao
 * 
 */
public class Partition {
	private long id;
	private String name;
	private List<ShardGroup> shardGroups;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ShardGroup> getShardGroups() {
		return shardGroups;
	}

	public void setShardGroups(List<ShardGroup> shardGroups) {
		this.shardGroups = shardGroups;
	}
}
