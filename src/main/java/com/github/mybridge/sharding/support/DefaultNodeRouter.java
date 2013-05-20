package com.github.mybridge.sharding.support;

import com.github.mybridge.sharding.NodeRouter;

public class DefaultNodeRouter implements NodeRouter {

    @Override
    public Node getNode(Shard shard, boolean writable) {
        shard.getNodes();
        return null;
    }

}
