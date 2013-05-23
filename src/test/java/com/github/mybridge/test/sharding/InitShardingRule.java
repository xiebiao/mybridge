package com.github.mybridge.test.sharding;

import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.mybridge.engine.JDBCProperties;
import com.github.mybridge.sharding.Router;

public class InitShardingRule {
	private static DruidDataSource ds;
	private int max = 10000;

	public InitShardingRule() {
		JDBCProperties jdbc = new JDBCProperties();

		ds = new DruidDataSource();
		ds.setDriver(jdbc.getDriver());
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/mybridge?characterEncoding=utf-8");
		ds.setUsername("root");
		ds.setPassword("wangzhu");
		ds.setTestOnBorrow(false);
	}

	public void initShardGroup() {

		String sql = "insert into shard_group(id,name,state,start_id,end_id) values(0,'user',"
				+ Router.OP_WRITE + ",0," + max + ")";
		System.out.println(sql);
		try {
			ds.getConnection().createStatement().execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initShard() {
		String sql0 = "insert into shard(id,name,hash_value,group_id,writable) values(0,'user_0','0',0,1)";
		String sql1 = "insert into shard(id,name,hash_value,group_id,writable) values(1,'user_1','1',0,1)";
		try {
			ds.getConnection().createStatement().execute(sql0);
			ds.getConnection().createStatement().execute(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initTable() {
		String sql0 = "insert into  fragment_table(id,name,shard_id,start_id,end_id) values(0,'user_0','0',0,5000)";
		String sql1 = "insert into  fragment_table(id,name,shard_id,start_id,end_id) values(1,'user_1','0',5000,10000)";

		String sql3 = "insert into  fragment_table(id,name,shard_id,start_id,end_id) values(2,'user_0','1',0,5000)";
		String sql4 = "insert into  fragment_table(id,name,shard_id,start_id,end_id) values(3,'user_1','1',5000,10000)";
		try {
			ds.getConnection().createStatement().execute(sql0);
			ds.getConnection().createStatement().execute(sql1);
			ds.getConnection().createStatement().execute(sql3);
			ds.getConnection().createStatement().execute(sql4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitShardingRule init = new InitShardingRule();
		init.initShardGroup();
		init.initShard();
		init.initTable();
	}

}
