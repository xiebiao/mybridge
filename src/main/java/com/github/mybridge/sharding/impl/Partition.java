package com.github.mybridge.sharding.impl;

import java.util.List;

import com.github.mybridge.sharding.Router;

/**
 * <h2>分区</h2>
 * <p>
 * 按照业务关联性较强的库表组成一个分区,一个分区内有多个分组。
 * </p>
 * 
 * @author xiebiao
 * 
 */
public class Partition {
	/**
	 * 分区id
	 */
	private long id;
	/**
	 * 分区名称
	 */
	private String name;
	/**
	 * 分区中所有分组
	 */
	private List<ShardGroup> shardGroups;

	public Partition() {

	}

	/**
	 * 获取一个可写入分组
	 * 
	 * @return
	 */
	public ShardGroup getShardGroup() {
		int size = shardGroups.size();
		for (int i = 0; i < size; i++) {
			ShardGroup sg = shardGroups.get(i);
			if (sg.canWrite()) {
				return sg;
			}
		}
		return null;
	}

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

	public String toString() {
		return "{id:" + id + ", name=" + this.name + ", shardGroups="
				+ shardGroups.toString() + "}";
	}
}
