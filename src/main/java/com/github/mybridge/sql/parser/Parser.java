package com.github.mybridge.sql.parser;

import net.sf.jsqlparser.statement.Statement;

public interface Parser {

    public long getId() throws ParserException;

    public Statement getStatement() throws ParserException;
}
