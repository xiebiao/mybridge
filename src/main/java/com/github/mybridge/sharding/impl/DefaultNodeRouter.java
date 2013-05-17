package com.github.mybridge.sharding.impl;

import com.github.mybridge.sharding.NodeRouter;

public class DefaultNodeRouter implements NodeRouter {

    @Override
    public Node getNode(Shard shard, boolean writable) {
        shard.getNodes();
        return null;
    }

}
