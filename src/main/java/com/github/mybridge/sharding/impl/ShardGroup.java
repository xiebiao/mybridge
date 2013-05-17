package com.github.mybridge.sharding.impl;

import java.util.List;

import com.github.mybridge.sharding.State;

/**
 * <h2>分组</h2>
 * 
 * <pre>
 *  	<code>endId - startId </code>：决定了分组的总容量
 * </pre>
 * @author xiebiao
 */
public class ShardGroup implements State {

    /**
     * 分组id
     */
    private long        id;
    /**
     * 分组名称
     */
    private String      groupName;
    /**
     * 是否可用
     */
    private boolean     writable;

    private long        startId;
    private long        endId;
    /**
     * 分组中所有shard
     */
    private List<Shard> shards;

    public ShardGroup() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String name) {
        this.groupName = name;
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

    @Override
    public boolean canWrite() {
        return true;
    }

    @Override
    public boolean canRead() {
        return true;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public String toString() {
        return "{id:" + this.id + ", groupName:" + this.groupName + ",writable=" + this.writable + " ,startId="
                + this.startId + " ,endId:" + this.endId + "}";
    }
}
