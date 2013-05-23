package com.github.mybridge.test.sql.parser;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

public class MySQLSQLParserTest {
	public static void test0() {
		String sql = "SELECT ID,NAME from USER where ID=1;";
		MySqlStatementParser parser = new MySqlStatementParser(sql);
		System.out.println(parser.parseCreate());
	}

	public static void test1() {
		String sql = "SELECT ID,NAME from USER where ID=1;";

		// parser得到AST
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(
				sql, JdbcUtils.MYSQL);
		List<SQLStatement> stmtList = parser.parseStatementList(); //

		// 将AST通过visitor输出
		StringBuilder out = new StringBuilder();
		SQLASTOutputVisitor visitor = SQLUtils.createFormatOutputVisitor(out,
				stmtList, JdbcUtils.MYSQL);

		for (SQLStatement stmt : stmtList) {
			stmt.accept(visitor);
			out.append(";");
		}

		System.out.println(out.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test0();

	}

}
