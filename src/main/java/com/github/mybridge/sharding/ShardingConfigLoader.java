package com.github.mybridge.sharding;

import java.util.List;


/**
 * 路由规则加载器
 * 
 * @author xiebiao
 */
public interface ShardingConfigLoader {
	public List<ShardGroup> load();
}
