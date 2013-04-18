package com.github.mybridge.sharding;

/**
 * 每个分组的容量一定
 * 
 * @author xiebiao
 * 
 */
public class ShardGroup {
	private long id;
	private String name;
	private boolean writable;
	private long startId;
	private long endId;

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

}
