package com.github.mybridge.engine;

public final class SQL {
	public static final SQL SELECT = new SQL("SELECT");
	public static final SQL INSERT = new SQL("INSERT");
	public static final SQL DELETE = new SQL("DELETE");
	public static final SQL UPDATE = new SQL("UPDATE");
	private String action;

	public SQL(String action) {
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}
}
