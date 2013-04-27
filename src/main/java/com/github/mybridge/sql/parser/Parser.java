package com.github.mybridge.sql.parser;

import net.sf.jsqlparser.statement.Statement;

public interface Parser {
	public String getId() throws ParserException;

	public Statement getStatement();
}
