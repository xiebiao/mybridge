package com.github.mybridge.sharding.impl;

import java.util.HashMap;
import java.util.Map;

import com.github.mybridge.engine.Engine;

public class Nodes {
	private Map<Long, Node> nodes = new HashMap<Long, Node>();

	public Nodes(Engine engine) {
	}

	public Node getNode(long shardId) {
		return nodes.get(shardId);
	}

	public void addNode(Node node) {
		nodes.put(node.getShardId(), node);
	}
}
