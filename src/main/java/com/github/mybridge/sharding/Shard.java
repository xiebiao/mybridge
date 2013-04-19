package com.github.mybridge.sharding;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.mybridge.engine.Database;
import com.github.mybridge.engine.Host;
import com.github.mybridge.engine.JDBCProperties;

/**
 * <h2>分片</h2>
 * 
 * @author xiebiao
 * 
 */
public class Shard implements Database {
	/**
	 * 分片id
	 */
	private long id;
	/**
	 * 分片名称
	 */
	private String name;
	/**
	 * id%分片数量 = 分片id
	 */
	private String hashValue;
	/**
	 * 对应ShardGroup中的id
	 */
	private long groupId;
	private boolean master = false;
	/**
	 * 分片中所有表
	 */
	private List<FragmentTable> tables;
	/** ------------------------------ 分片对应的物理信息 */
	private static DruidDataSource ds;
	private Host host;

	public Shard(int id, String name, long groupId, Host host) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
		this.host = host;
		JDBCProperties jdbc = new JDBCProperties();
		ds = new DruidDataSource();
		ds.setDriver(jdbc.getDriver());
		String database = this.name + this.id;
		ds.setUrl("jdbc:mysql://" + host + "/" + database);
		ds.setUsername(jdbc.getUser());
		ds.setPassword(jdbc.getPassword());
		ds.setTestOnBorrow(false);
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

	public List<FragmentTable> getTables() {
		return tables;
	}

	public void setTables(List<FragmentTable> tables) {
		this.tables = tables;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	@Override
	public Host getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAlive() {

		return ds.isEnable();
	}

	@Override
	public void shutdown() {
		ds.close();
	}

	public String toString() {
		return "{ id:" + this.id + ", name:" + this.name + ", host:"
				+ this.host + ", tables:" + this.tables + "}";
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}
}
