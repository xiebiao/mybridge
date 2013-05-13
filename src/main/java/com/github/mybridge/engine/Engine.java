package com.github.mybridge.engine;

import java.util.List;

import com.github.mybridge.Lifecycle;
import com.github.mybridge.sharding.impl.Node;

public interface Engine extends Lifecycle {

    public List<Node> getNodes();
}
