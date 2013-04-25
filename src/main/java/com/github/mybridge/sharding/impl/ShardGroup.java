package com.github.mybridge.sharding.impl;

import java.util.List;

import com.github.mybridge.sharding.State;

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
public class ShardGroup implements State {
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
	private int writable;

	private long startId;
	private long endId;
	/**
	 * 分组中所有shard
	 */
	private List<Shard> shards;

	public ShardGroup() {
	}

	public ShardGroup(int id, String name, int state, long startId, long endId) {
		this.id = id;
		this.name = name;
		this.writable = state;
		this.startId = startId;
		this.endId = endId;
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

	public void addShard(Shard shard) {
		synchronized (this) {
			this.shards.add(shard);
		}
	}

	public String toString() {
		return "{id:" + this.id + ", name:" + this.name + ", startId:"
				+ this.startId + " ,state=" + this.writable + " , endId:"
				+ this.endId + "}";
	}

	@Override
	public boolean canWrite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRead() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getWritable() {
		return writable;
	}

	public void setWritable(int writable) {
		this.writable = writable;
	}

}
