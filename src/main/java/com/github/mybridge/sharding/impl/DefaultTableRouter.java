package com.github.mybridge.sharding.impl;

import java.util.List;

import com.github.mybridge.sharding.TableRouter;

public class DefaultTableRouter implements TableRouter {

	@Override
	public FragmentTable getTable(Shard shard, long id) {
		List<FragmentTable> tables = shard.getTables();
		int size = tables.size();
		for (int i = 0; i < size; i++) {
			FragmentTable table = tables.get(i);
			if (id > table.getStartId() && id < table.getEndId()) {
				return table;
			}
		}
		return null;
	}

}
