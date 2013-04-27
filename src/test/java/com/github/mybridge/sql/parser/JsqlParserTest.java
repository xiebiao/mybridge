package com.github.mybridge.sql.parser;

import java.io.StringReader;

import junit.framework.TestCase;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class JsqlParserTest extends TestCase {
	public void testDrop() {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		String statement = "DROP TABLE mytab";
		try {
			Drop drop = (Drop) parserManager.parse(new StringReader(statement));
			assertEquals("TABLE", drop.getType());
			assertEquals("mytab", drop.getName());
			assertEquals(statement, "" + drop);
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testSelect() {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		String statement = "Select * from user where id='sss'";
		try {

			Select select = (Select) parserManager.parse(new StringReader(
					statement));

			Expression s = ((PlainSelect) select.getSelectBody()).getWhere();
			WhereExpressionVisitor visitor = new WhereExpressionVisitor(s);
			//visitor.eval(values)
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
