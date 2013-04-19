package com.github.mybridge.engine;

import com.github.mybridge.sharding.Partition;

/**
 * SQL解析器
 * 
 * @author xiebiao
 */
public class SqlParser implements Parser {
	private Engine engine;

	public SqlParser(Engine engine) {

	}

	@Override
	public Partition getPartition(String database) {
		// engine.getServer(sql, database)
		return null;
	}

	@Override
	public boolean isWrite() {
		// TODO Auto-generated method stub
		return false;
	}

}
