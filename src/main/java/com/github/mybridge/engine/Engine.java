package com.github.mybridge.engine;

import com.github.mybridge.Lifecycle;
import com.github.mybridge.sharding.ShardGroupRouter;
import com.github.mybridge.sharding.ShardRouter;
import com.github.mybridge.sharding.TableRouter;
import com.github.mybridge.sharding.impl.Node;

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

    public Node getNode(String sql);

    public void setTableRouter(TableRouter tableRouter);

    public void setShardRouter(ShardRouter shardRouter);

    public void setShardGroupRouter(ShardGroupRouter shardGroupRouter);
}
