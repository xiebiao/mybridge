package com.github.mybridge.test.sql.parser;

import java.io.StringReader;

import com.github.mybridge.sql.parser.WhereExpressionVisitor;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import junit.framework.Assert;
import junit.framework.TestCase;

public class WhereExpressionVisitorTest extends TestCase {

    public void test_select() {
        long id = 10000;
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        String statement = "Select * from user where id=" + id;
        try {
            Select select = (Select) parserManager.parse(new StringReader(statement));
            Expression where = ((PlainSelect) select.getSelectBody()).getWhere();
            WhereExpressionVisitor visitor = new WhereExpressionVisitor(where);
            Assert.assertEquals(id, visitor.getId());
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
}
