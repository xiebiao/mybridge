package com.github.mybridge.sharding.support;

import java.util.List;

import com.github.mybridge.sharding.NotFoundTableException;
import com.github.mybridge.sharding.Shard;
import com.github.mybridge.sharding.TableRouter;

public class DefaultTableRouter implements TableRouter {

    @Override
    public FragmentTable getTable(Shard shard, long id) throws NotFoundTableException {
        List<FragmentTable> tables = shard.getTables();
        int size = tables.size();
        for (int i = 0; i < size; i++) {
            FragmentTable table = tables.get(i);
            if (id > table.getStartId() && id < table.getEndId()) {
                return table;
            }
        }
        throw new NotFoundTableException("id:" + id + " 找不到表信息");
    }

}
