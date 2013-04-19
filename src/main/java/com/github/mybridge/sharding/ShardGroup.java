package com.github.mybridge.sharding;

import java.util.List;

/**
 * <h2>分组</h2>
 * 
 * <pre>
 *  	<code>endId - startId </code>：决定了分组的总容量
 * </pre>
 * 
 * @author xiebiao
 * 
 */
public class ShardGroup {
	/**
	 * 分组id
	 */
	private long id;
	/**
	 * 分组名称
	 */
	private String name;
	/**
	 * 是否可用
	 */
	private boolean writable;

	private long startId;
	private long endId;
	/**
	 * 分组中所有shard
	 */
	private List<Shard> shards;

	/**
	 * 获取可写分组
	 * 
	 * @param businessId
	 * @return
	 */
	public Shard getWriteShard(String businessId) {
		int size = shards.size();
		for (int i = 0; i < size; i++) {
			Shard s = shards.get(i);
			if (s.isWritable() && s.isAlive()
					&& getMod(businessId).equals(s.getHashValue())) {
				return s;
			}
		}
		return null;
	}

	private String getMod(String businessId) {
		int mod = Integer.valueOf(businessId) % this.shards.size();
		return String.valueOf(mod);
	}

	/**
	 * 获取一个读Shard(默认一个master对应一个slave)
	 * 
	 * @return
	 */
	public Shard getReadShard(String businessId) {
		int size = shards.size();
		for (int i = 0; i < size; i++) {
			Shard s = shards.get(i);
			if (!s.isWritable() && s.isAlive()
					&& getMod(businessId).equals(s.getHashValue())) {
				return s;
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

	public boolean isWritable() {
		return writable;
	}

	public void setWritable(boolean writable) {
		this.writable = writable;
	}

	public long getStartId() {
		return startId;
	}

	public void setStartId(long startId) {
		this.startId = startId;
	}

	public long getEndId() {
		return endId;
	}

	public void setEndId(long endId) {
		this.endId = endId;
	}

	public List<Shard> getShards() {
		return shards;
	}

	public void setShards(List<Shard> shards) {
		this.shards = shards;
	}

	public String toString() {
		return "{id:" + this.id + ", name:" + this.name + ", startId:"
				+ this.startId + ", endId:" + this.endId + ", shards:"
				+ this.shards + "}";
	}

}
