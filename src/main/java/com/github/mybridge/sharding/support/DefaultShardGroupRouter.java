package com.github.mybridge.sharding.support;

import java.util.List;

import com.github.mybridge.sharding.NotFoundShardGroupException;
import com.github.mybridge.sharding.ShardGroup;
import com.github.mybridge.sharding.ShardGroupRouter;

public class DefaultShardGroupRouter implements ShardGroupRouter {

    @Override
    public ShardGroup getShardGroup(List<ShardGroup> shardGroup, boolean writable, long id)
            throws NotFoundShardGroupException {
        int size = shardGroup.size();
        for (int i = 0; i < size; i++) {
            ShardGroup group = shardGroup.get(i);
            if (id > group.getStartId() && id < group.getEndId()) {
                if (group.readable() == group.writable() == writable) {
                    return group;
                }
            }
        }
        throw new NotFoundShardGroupException("id:" + id + " 找不到分组信息");
    }
}
