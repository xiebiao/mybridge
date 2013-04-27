package com.github.mybridge.sharding.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.mybridge.engine.JDBCProperties;
import com.github.mybridge.sharding.ShardingRuleLoader;

/**
 * 从数据库加载路由规则
 * 
 * @author xiebiao
 */
public class ShardingRuleLoaderImpl implements ShardingRuleLoader {
	private static final String LOAD_GROUP_SQL = "SELECT * FROM shard_group";
	private static final String LOAD_SHARD_SQL = "SELECT * FROM shard WHERE group_id=?";
	private static final String LOAD_TABLE_SQL = "SELECT * FROM fragment_table WHERE shard_id=?";
	private static DruidDataSource ds;

	public ShardingRuleLoaderImpl() {
		JDBCProperties jdbc = new JDBCProperties();
		ds = new DruidDataSource();
		ds.setDriver(jdbc.getDriver());
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/mybridge?characterEncoding=utf-8");
		ds.setUsername("root");
		ds.setPassword("wangzhu");
		ds.setTestOnBorrow(false);
	}

	@Override
	public List<ShardGroup> load() {
		List<ShardGroup> list = new ArrayList<ShardGroup>();
		try {
			Connection conn = ds.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(LOAD_GROUP_SQL);
			while (rs.next()) {
				ShardGroup sg = new ShardGroup();
				sg.setName(rs.getString("name"));
				sg.setId(rs.getLong("id"));
				sg.setStartId(rs.getLong("start_id"));
				sg.setEndId(rs.getLong("end_id"));
				sg.setShards(this.loadShard(sg.getId()));
				sg.setWritable(rs.getInt("writable"));
				list.add(sg);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private List<FragmentTable> loadTable(long shardId) {
		List<FragmentTable> tables = new ArrayList<FragmentTable>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement st = conn.prepareStatement(LOAD_TABLE_SQL);
			st.setLong(1, shardId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				FragmentTable table = new FragmentTable();
				table.setId(rs.getLong("id"));
				table.setName(rs.getString("name"));
				table.setStartId(rs.getLong("start_id"));
				table.setEndId(rs.getLong("end_id"));
				tables.add(table);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	private List<Shard> loadShard(long groupId) {
		List<Shard> shards = new ArrayList<Shard>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement st = conn.prepareStatement(LOAD_SHARD_SQL);
			st.setLong(1, groupId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Shard shard = new Shard();
				shard.setId(rs.getLong("id"));
				shard.setName(rs.getString("name"));
				String hash = rs.getString("hash_value");

				Set<String> set = new HashSet<String>();
				if (hash.indexOf(",") == -1) {
					set.add(hash);
				} else {
					String[] ss = hash.split(",");
					for (String s : ss) {
						set.add(s);
					}
				}
				shard.setGroupId(rs.getLong("group_id"));
				shard.setHashValue(set);
				shard.setTables(this.loadTable(shard.getId()));
				shards.add(shard);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return shards;
	}

	public static final void main(String args[]) {
		ShardingRuleLoaderImpl srl = new ShardingRuleLoaderImpl();
		System.out.println(srl.load());
	}
}
