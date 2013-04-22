package com.github.mybridge.sharding.impl;

import com.github.mybridge.sharding.Router;

public abstract class AbstractRouter implements Router {
	protected long mod(int total, long id) {
		return id % total;
	}
}
