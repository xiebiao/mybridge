package com.github.mybridge.engine;

import com.github.mybridge.Lifecycle;
import com.github.mybridge.Pair;
import com.github.mybridge.sharding.Node;
import com.github.mybridge.sharding.NodeRouter;
import com.github.mybridge.sharding.ShardGroupRouter;
import com.github.mybridge.sharding.ShardRouter;
import com.github.mybridge.sharding.TableRouter;

/**
 * <pre>
 * 功能：
 *   1.加载节点
 *   2.SQL解析
 *   3.节点路由，表路由
 * </pre>
 * @author xiebiao
 */
public interface Engine extends Lifecycle {

    public Pair<Node, String> match(String sql);

    public void setTableRouter(TableRouter tableRouter);

    public void setShardRouter(ShardRouter shardRouter);

    public void setShardGroupRouter(ShardGroupRouter shardGroupRouter);

    public void setNodeRouter(NodeRouter nodeRouter);
}
