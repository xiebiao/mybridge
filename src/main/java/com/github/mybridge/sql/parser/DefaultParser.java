package com.github.mybridge.sql.parser;

import java.io.StringReader;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import com.github.mybridge.sharding.SqlType;

public class DefaultParser extends AbstractParser implements Parser {

    private String                        sql;
    private Statement                     statement;
    private String                        idName = "id";
    private long                          idValue;
    private String                        tableName;
    private static final org.slf4j.Logger LOG    = org.slf4j.LoggerFactory.getLogger(DefaultParser.class);

    public DefaultParser(String sql, String idName) {
        this.sql = sql;
        this.idName = idName;
        CCJSqlParserManager parser = new CCJSqlParserManager();
        try {
            statement = parser.parse(new StringReader(sql));
            System.out.println("statement:" + statement);

            if (statement instanceof Insert) {
                parseInsert();
            } else if (statement instanceof Select) {
                parseSelect();
            }// 后面再支持Delete,Update
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getId() throws ParserException {
        return this.idValue;
    }

    @Override
    public SqlType getType() throws UnsupportSqlTypeException {
        if (statement instanceof Select) {
            return SqlType.READ;
        } else if (statement instanceof Insert) {
            return SqlType.WRITE;
        } else if (statement instanceof Update) {
            return SqlType.WRITE;
        } else if (statement instanceof Delete) {
            return SqlType.WRITE;
        }
        throw new UnsupportSqlTypeException("不支持Sql:" + this.sql);
    }

    private void parseSelect() {
        Select select = (Select) statement;
        PlainSelect ps = (PlainSelect) select.getSelectBody();
        this.tableName = ps.getFromItem().toString();// 不支持多表查询
        Expression where = ps.getWhere();
        WhereExpressionVisitor visitor = new WhereExpressionVisitor(where);
        this.idValue = visitor.getId();
    }

    private void parseInsert() {
        Insert insert = (Insert) statement;
        this.tableName = insert.getTable().getName();
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
        this.idValue = iv.getValue();
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String replace(String tableName) throws ParserException {
        // 只能用正则替换了
        return null;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
