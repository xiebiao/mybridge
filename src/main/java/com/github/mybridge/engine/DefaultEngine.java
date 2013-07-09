package com.github.mybridge.engine;

import java.util.List;

import com.github.mybridge.Constants;
import com.github.mybridge.LifecycleException;
import com.github.mybridge.sharding.Node;
import com.github.mybridge.sharding.NodeExecuter;
import com.github.mybridge.sharding.NodeRouter;
import com.github.mybridge.sharding.Shard;
import com.github.mybridge.sharding.ShardGroup;
import com.github.mybridge.sharding.ShardGroupRouter;
import com.github.mybridge.sharding.ShardRouter;
import com.github.mybridge.sharding.ShardingConfigLoader;
import com.github.mybridge.sharding.TableRouter;
import com.github.mybridge.sharding.support.FragmentTable;
import com.github.mybridge.sharding.support.ShardingConfigLoaderImpl;
import com.github.mybridge.sql.parser.DefaultParser;
import com.github.mybridge.sql.parser.Parser;

public class DefaultEngine implements Engine {

    private List<ShardGroup>              shardGroups;
    private TableRouter                   tableRouter;
    private ShardGroupRouter              shardGroupRouter;
    private ShardRouter                   shardRouter;
    private NodeRouter                    nodeRouter;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultEngine.class);

    public DefaultEngine() {

    }

    @Override
    public void init() throws LifecycleException {
        ShardingConfigLoader configLoader = new ShardingConfigLoaderImpl();
        this.shardGroups = configLoader.load();
    }

    @Override
    public void start() throws LifecycleException {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() throws LifecycleException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() throws LifecycleException {
        // TODO Auto-generated method stub

    }

    @Override
    public NodeExecuter getNodeExecuter(String sql) {
        Parser parser = new DefaultParser(sql, Constants.DEFAULT_ID_NAME);
        NodeExecuter ne = null;
        try {
            long id = parser.getId();
            ShardGroup group = this.shardGroupRouter.getShardGroup(shardGroups, true, id);
            Shard shard = this.shardRouter.getShard(group, id);
            FragmentTable table = this.tableRouter.getTable(shard, id);
            final Node node = this.nodeRouter.getNode(shard, parser.getOperation());
            final String _sql = parser.replace(table.getTableName() + "_" + table.getId());
            logger.debug("sql:{}, find in Node:{}, excute sql:", sql, node, _sql);
            ne = new NodeExecuter() {

                @Override
                public Node getNode() {
                    // TODO Auto-generated method stub
                    return node;
                }

                @Override
                public String getSql() {
                    // TODO Auto-generated method stub
                    return _sql;
                }
            };
        } catch (Exception e) {}

        return ne;
    }

    @Override
    public void setTableRouter(TableRouter tableRouter) {
        this.tableRouter = tableRouter;

    }

    @Override
    public void setShardRouter(ShardRouter shardRouter) {
        this.shardRouter = shardRouter;

    }

    @Override
    public void setShardGroupRouter(ShardGroupRouter shardGroupRouter) {
        this.shardGroupRouter = shardGroupRouter;
    }

    @Override
    public void setNodeRouter(NodeRouter nodeRouter) {
        this.nodeRouter = nodeRouter;

    }
}
