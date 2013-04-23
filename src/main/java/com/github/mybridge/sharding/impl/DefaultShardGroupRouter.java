package com.github.mybridge.sharding.impl;

import java.util.List;

import com.github.mybridge.sharding.ShardGroupRouter;

public class DefaultShardGroupRouter implements ShardGroupRouter {

	@Override
	public ShardGroup getShardGroup(List<ShardGroup> shardGroup, int state,
			long id) {
		int size = shardGroup.size();
		for (int i = 0; i < size; i++) {
			ShardGroup sg = shardGroup.get(i);
			if (id > sg.getStartId() && id < sg.getEndId()) {			
				if (sg.getState() == state) {				
					return sg;
				}
			}
		}
		return null;
	}
}
