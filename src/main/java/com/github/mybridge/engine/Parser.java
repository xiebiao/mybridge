package com.github.mybridge.engine;

import com.github.mybridge.sharding.Partition;

/**
 * 解析器
 * 
 * @author xiebiao
 */
public interface Parser {
	/**
	 * 获取分区
	 * 
	 * @param database
	 *            从JDBC传入
	 * @return
	 */
	Partition getPartition(String database);

	/**
	 * 是否写操作
	 * 
	 * @return
	 */
	boolean isWrite();

}
