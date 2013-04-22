package com.github.mybridge.sharding.impl;

import java.util.List;

import com.github.mybridge.sharding.ShardRouter;
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
	private State state;

	private long startId;
	private long endId;
	/**
	 * 分组中所有shard
	 */
	private List<Shard> shards;
	private ShardRouter shardRouter;

	public ShardGroup(int id, String name, State state, long startId, long endId) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.startId = startId;
		this.endId = endId;
		shardRouter = new DefaultShardRouter();
	}

	public Shard getShard(State state, long id) {
		int size = shards.size();
		for (int i = 0; i < size; i++) {
			Shard s = shards.get(i);
			if (!s.isWritable() && s.isAlive()) {
				return shardRouter.getShard(this, state, id);
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

	public State getSate() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
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
				+ this.startId + ", endId:" + this.endId + ", shards:"
				+ this.shards + "}";
	}

}
