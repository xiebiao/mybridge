package com.github.mybridge.sharding;

import com.github.mybridge.sharding.impl.Node;
import com.github.mybridge.sharding.impl.Shard;

/**
 * 节点路由
 * @author xiebiao
 */
public interface NodeRouter extends Router {

    /**
     * 返回指定状态的Node
     * @param shard
     * @param writable 是否可写
     * @return
     */
    public Node getNode(Shard shard, boolean writable);
}
