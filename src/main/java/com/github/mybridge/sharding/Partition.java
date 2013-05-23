package com.github.mybridge.sharding;

import java.util.List;


/**
 * <h2>分区</h2>
 * <p>
 * 按照业务关联性较强的库表组成一个分区,一个分区内有多个分组。
 * </p>
 * @author xiebiao
 */
public class Partition {

    /**
     * 分区id
     */
    private long             id;
    /**
     * 分区名称
     */
    private String           name;
    /**
     * 分区中所有分组
     */
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
