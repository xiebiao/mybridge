package com.github.mybridge.engine;

import java.util.List;

import net.sf.jsqlparser.statement.Statement;

import com.github.mybridge.LifecycleException;
import com.github.mybridge.sharding.ShardGroupRouter;
import com.github.mybridge.sharding.ShardRouter;
import com.github.mybridge.sharding.ShardingConfigLoader;
import com.github.mybridge.sharding.TableRouter;
import com.github.mybridge.sharding.impl.Node;
import com.github.mybridge.sharding.impl.ShardGroup;
import com.github.mybridge.sharding.impl.ShardingConfigLoaderImpl;
import com.github.mybridge.sql.parser.DefaultParser;
import com.github.mybridge.sql.parser.ParserException;

public class DefaultEngine implements Engine {

    private List<ShardGroup> shardGroups;
    private TableRouter      tableRouter;
    private ShardGroupRouter shardGroupRouter;
    private ShardRouter      shardRouter;

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
    public Node getNode(String sql) {
        DefaultParser parser = new DefaultParser(sql);
        try {
            Statement st = parser.getStatement();

            long id = parser.getId();

        } catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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

}
