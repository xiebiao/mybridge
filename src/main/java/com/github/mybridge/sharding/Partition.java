package com.github.mybridge.sharding;

import java.util.List;

/**
 * 一个分区内有多个分组
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
			if (sg.isWritable()) {
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
