package com.github.mybridge.sharding;

import com.github.mybridge.sharding.impl.FragmentTable;
import com.github.mybridge.sharding.impl.Shard;

/**
 * 分表路由
 * 
 * @author xiebiao
 */
public interface TableRouter extends Router {
	public FragmentTable getTable(Shard shard, long id);
}
