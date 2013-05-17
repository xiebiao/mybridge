package com.github.mybridge.sql.parser;

import java.io.StringReader;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class DefaultParser implements Parser {

    private String                        sql;
    private Statement                     statement;
    private String                        idName = "id";

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultParser.class);

    public DefaultParser() {

    }

    public DefaultParser(String sql) {
        this.sql = sql;
    }

    @Override
    public long getId() throws ParserException {
        if (statement == null) {
            this.getStatement();
        }
        if (statement instanceof Insert) {
            return parseInsert();
        } else if (statement instanceof Select) {
            return parseSelect();
        }

        return 0;
    }

    @Override
    public Statement getStatement() throws ParserException {
        if (statement != null) return statement;
        CCJSqlParserManager parser = new CCJSqlParserManager();
        try {
            statement = parser.parse(new StringReader(sql));
            return this.statement;
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return statement;
    }

    private long parseSelect() {
        Select select = (Select) statement;
        Expression where = ((PlainSelect) select.getSelectBody()).getWhere();
        WhereExpressionVisitor visitor = new WhereExpressionVisitor(where);
        return visitor.getId();
    }

    private long parseInsert() {
        Insert insert = (Insert) statement;
        List columns = insert.getColumns();
        ItemsList values = insert.getItemsList();
        int index = 0;
        for (; index < columns.size(); index++) {
            if (idName.equals(String.valueOf(columns.get(index)))) {
                break;
            }
        }
        IdItemsListVisitor iv = new IdItemsListVisitor(index);
        values.accept(iv);
        return iv.getValue();

    }

    @Override
    public void setIdName(String name) {
        this.idName = name;

    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
