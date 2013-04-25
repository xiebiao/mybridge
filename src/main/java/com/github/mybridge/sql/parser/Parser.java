package com.github.mybridge.sql.parser;

import com.github.mybridge.sql.statement.Statement;

public interface Parser {
	public String getId() throws ParserException;

	public Statement getStatement();
}
