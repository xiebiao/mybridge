package com.github.mybridge.sharding.support;

import java.util.List;
import java.util.Set;

import com.github.mybridge.sharding.NotFoundShardException;
import com.github.mybridge.sharding.Shard;
import com.github.mybridge.sharding.ShardGroup;
import com.github.mybridge.sharding.ShardRouter;

public class DefaultShardRouter extends AbstractRouter implements ShardRouter {

    public DefaultShardRouter() {

    }

    @Override
    public Shard getShard(ShardGroup shardGroup, long id) throws NotFoundShardException {
        List<Shard> shards = shardGroup.getShards();
        int size = shards.size();
        for (int i = 0; i < size; i++) {
            Shard shard = shards.get(i);
            Set<String> hashs = shard.getHashValue();
            for (String hash : hashs) {
                if (hash.equals(String.valueOf(mod(size, id)))) {
                    return shard;
                }
            }
        }
        throw new NotFoundShardException("id:" + id + " 找不到分片信息");
    }

}
