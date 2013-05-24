package com.github.mybridge.sharding.support;

/**
 * <h2>分表信息</h2>
 * 
 * <pre>
 * 		<code>endId - startId</code>：决定了分表的容量
 * </pre>
 * @author xiebiao
 */
public class FragmentTable {

    private long   id;
    /**
     * 分片id
     */
    private long   shardId;
    /**
     * 表名=id+
     */
    private String tableName;
    /**
     * 原始
     */
    private String originalName;
    private long   startId;
    private long   endId;

    public FragmentTable() {}

    public FragmentTable(String tableName) {
        this.tableName = tableName;
    }

    public FragmentTable(long id, String tableName) {
        this.id = 0;
        this.tableName = tableName;
    }

    public FragmentTable(long id, String tableName, long shardId, long startId, long endId) {
        this.id = id;
        this.tableName = tableName;
        this.startId = startId;
        this.shardId = shardId;
        this.startId = startId;
        this.endId = endId;
    }

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String name) {
        this.tableName = name;
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

    public String toString() {
        return "{id:" + this.id + ", tableName:" + this.tableName + ", groupId:" + this.getShardId() + ", startId:"
                + this.shardId + ", endId:" + this.endId + "}";
    }
}
