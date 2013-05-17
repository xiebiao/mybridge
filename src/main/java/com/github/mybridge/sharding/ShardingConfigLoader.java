package com.github.mybridge.sharding;

import java.util.List;

import com.github.mybridge.sharding.impl.ShardGroup;

/**
 * 路由规则加载器
 * 
 * @author xiebiao
 */
public interface ShardingConfigLoader {
	public List<ShardGroup> load();
}
