package com.github.mybridge.sharding.impl;

import java.util.List;
import java.util.Set;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.mybridge.engine.JDBCProperties;
import com.github.mybridge.sharding.TableRouter;

/**
 * <h2>分片</h2>
 * 
 * @author xiebiao
 * 
 */
public class Shard {
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
	public Set<String> hashValue;
	/**
	 * 对应ShardGroup中的id
	 */
	private long groupId;

	/**
	 * 分片中所有表
	 */
	private List<FragmentTable> tables;
	/** ------------------------------ 分片对应的物理信息 */
	private static DruidDataSource ds;
	private TableRouter tableRouter;

	public Shard() {
	}

	public Shard(int id, String name, long groupId) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
		JDBCProperties jdbc = new JDBCProperties();
		ds = new DruidDataSource();
		ds.setDriver(jdbc.getDriver());
		String database = this.name + this.id;
		// ds.setUrl("jdbc:mysql://" + host + "/" + database);
		ds.setUsername(jdbc.getUser());
		ds.setPassword(jdbc.getPassword());
		ds.setTestOnBorrow(false);
		// tableRouter
		tableRouter = new DefaultTableRouter();
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

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public List<FragmentTable> getTables() {
		return tables;
	}

	public FragmentTable getTable(long id) {
		return this.tableRouter.getTable(this, id);
	}

	public void setTables(List<FragmentTable> tables) {
		this.tables = tables;
	}

	public Set<String> getHashValue() {
		return hashValue;
	}

	public void setHashValue(Set<String> hash) {
		this.hashValue = hash;
	}
}
