package org.github.mybridge.engine;

public class SQLParser implements Parser {

	@Override
	public SQL parse(String sql) {
		if (sql == null || sql.equals("")) {
			return new SQL("ERROR");
		}
		sql = sql.trim();
		String[] sqlArr=sql.split(" ");
		return null;
	}

}
