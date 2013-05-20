package com.github.mybridge.sharding;

import com.github.mybridge.sharding.support.FragmentTable;
import com.github.mybridge.sharding.support.Shard;

/**
 * 分表路由
 * 
 * @author xiebiao
 */
public interface TableRouter extends Router {
	public FragmentTable getTable(Shard shard, long id);
}
