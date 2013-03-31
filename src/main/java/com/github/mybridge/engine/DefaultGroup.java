package com.github.mybridge.engine;

public class DefaultGroup implements Group {
	private int id;

	public DefaultGroup(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

}
