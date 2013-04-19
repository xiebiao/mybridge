package com.github.mybridge.engine;

import java.util.List;

import com.github.mybridge.exception.ParserException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SqlParserTest extends TestCase {
	public void test() {
		String sql = "select * from user where id=1000";
		Engine engine = new DefaultEngine();
		SqlParser s = new SqlParser(sql, engine);
		try {
			List<String> tables = s.getTables();
			Assert.assertEquals(1, tables.size());
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
	public void test1() {
		String sql = "SELECT * FROM user where id=1000";
		Engine engine = new DefaultEngine();
		SqlParser s = new SqlParser(sql, engine);
		try {
			List<String> tables = s.getTables();
			Assert.assertEquals(1, tables.size());
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}
