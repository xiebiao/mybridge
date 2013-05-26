package com.github.mybridge.sharding;

/**
 * 节点路由:
 * <p>
 * 在分片中选择一个可用的节点
 * </p>
 * @author xiebiao
 */
public interface NodeRouter extends Router {

    /**
     * 返回指定状态的Node
     * @param shard
     * @param writable 可写
     * @return
     */
    public Node getNode(Shard shard, Operation operation) throws NotFoundNodeException;
}
