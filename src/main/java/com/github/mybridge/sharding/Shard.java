package com.github.mybridge.sharding;

/**
 * 分片代表一个数据源实例
 * 
 * @author xiebiao
 * 
 */
public class Shard {
	private long id;
	private String name;
	private String hashValue;
	private long groupId;

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

	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

}
