package com.github.mybridge.sql.parser;

import java.io.StringReader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

public class DefaultParser implements Parser {

    private String    sql;
    private Statement statement;

    public DefaultParser(String sql) {
        this.sql = sql;
    }

    @Override
    public long getId() throws ParserException {
        return 0;
    }

    @Override
    public Statement getStatement() throws ParserException {
        CCJSqlParserManager parser = new CCJSqlParserManager();
        try {
            statement = parser.parse(new StringReader(sql));
            return this.statement;
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return null;
    }

}
