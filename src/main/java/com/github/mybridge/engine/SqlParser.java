package com.github.mybridge.engine;

import java.util.ArrayList;
import java.util.List;

import com.github.mybridge.exception.ParserException;

/**
 * <h2>SQL解析器<h2>
 * 
 * @author xiebiao
 * 
 */
public class SqlParser implements Parser {
	private String sql;
	private Engine engine;
	private String businessId;
	public static final String UPDATE = "^update ";

	public SqlParser(String sql, Engine engine) {
		if (null == sql) {
			throw new java.lang.NullPointerException("Excute sql is null");
		}
		this.sql = sql;
		this.engine = engine;
	}

	@Override
	public boolean isWrite() {

		return false;
	}

	@Override
	public List<String> getTables() throws ParserException {
		/**
		 * 需要正则处理<br/>
		 * 注意SQL中大小写敏感问题<br/>
		 * 例如：<br/>
		 * select * from user where id=1000;<br/>
		 * SELECT * FROM user WHERE id=1000;<br/>
		 * SElect* FROM user WHere id=1000;<br/>
		 */
		int start = sql.indexOf("from");
		int end = sql.indexOf("where");
		if (end == -1) {
			throw new ParserException(sql + ": no Where case");
		}
		String tables = sql.substring(start, end);
		String[] tablesArr = tables.split(",");
		List<String> ts = new ArrayList<String>();

		for (String table : tablesArr) {
			ts.add(table);
		}
		return ts;
	}

	@Override
	public List<String> getFileds() throws ParserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() throws ParserException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 需要正则处理<br/>
	 */
	private void parse() {

	}
}
