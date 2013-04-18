package com.github.mybridge.sharding;

/**
 * 表分段信息
 * 
 * @author xiebiao
 * 
 */
public class FragmentTable {
	private long id;
	/**
	 * 分片id
	 */
	private long shardId;
	/**
	 * 表名
	 */
	private String name;
	private long startId;
	private long endId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getShardId() {
		return shardId;
	}
	public void setShardId(long shardId) {
		this.shardId = shardId;
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
}
