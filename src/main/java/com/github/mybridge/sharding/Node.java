package com.github.mybridge.sharding;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;


/**
 * 数据库节点:
 * <p>
 * 同分片下的节点是同级的，只有Master/Slave之分
 * </p>
 * @author xiebiao
 */
public class Node implements State, ConnectionPool {

    private long    id;
    private String  ip;
    private int     port;
    private long    groupId;
    private long    shardId;
    private boolean writable;
    private boolean available;
    private Date    createTime;
    private Date    updateTime;

    public Node() {
        port = 3306;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getShardId() {
        return shardId;
    }

    public void setShardId(long shardId) {
        this.shardId = shardId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean writable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean readable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return "{id:" + this.id + ", ip:" + this.ip + ", port:" + this.port + ", writable:" + this.writable + "}";
    }

}
