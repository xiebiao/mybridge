package com.github.mybridge.sharding.support;

import java.util.List;

import com.github.mybridge.sharding.Node;
import com.github.mybridge.sharding.NodeRouter;
import com.github.mybridge.sharding.Shard;

public class DefaultNodeRouter implements NodeRouter {

    @Override
    public Node getNode(Shard shard, boolean writable) {
        List<Node> nodes = shard.getNodes();
        for (Node node : nodes) {
            if (node.writable() == writable) {
                return node;
            }
        }
        return null;
    }

}
