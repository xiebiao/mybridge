package com.github.mybridge.engine;

public class DefaultGroup implements Group {
	private String name;

	public DefaultGroup(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
