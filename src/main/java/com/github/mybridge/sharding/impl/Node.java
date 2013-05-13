package com.github.mybridge.sharding.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import com.github.mybridge.Lifecycle;
import com.github.mybridge.LifecycleException;
import com.github.mybridge.sharding.ConnectionPool;
import com.github.mybridge.sharding.State;

/**
 * 数据库节点
 * @author xiebiao
 */
public class Node implements Lifecycle, State, ConnectionPool {

    private String ip;
    private int    port;
    private long   groupId;
    private long   shardId;
    private Date   createTime;
    private Date   updateTime;

    public Node() {

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
    public Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void start() throws LifecycleException {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() throws LifecycleException {
        // TODO Auto-generated method stub

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

}
