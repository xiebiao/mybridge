package com.github.mybridge.sharding.support;

import java.util.List;

import com.github.mybridge.sharding.Node;
import com.github.mybridge.sharding.NodeRouter;
import com.github.mybridge.sharding.NotFoundNodeException;
import com.github.mybridge.sharding.Operation;
import com.github.mybridge.sharding.Shard;

public class DefaultNodeRouter implements NodeRouter {

    @Override
    public Node getNode(Shard shard, Operation operation) throws NotFoundNodeException {
        List<Node> nodes = shard.getNodes();
        for (Node node : nodes) {
            switch (operation) {
                case WRITE:
                    if (node.writable()) {
                        return node;
                    }
                case READ:
                    if (node.readable()) {
                        return node;
                    }
            }
        }
        throw new NotFoundNodeException("Shard:" + shard + " can't find a Node.");
    }

}
