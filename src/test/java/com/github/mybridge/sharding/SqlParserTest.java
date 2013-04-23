package com.github.mybridge.sharding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParserTest {
	public static final String UPDATE = "(update)(\\s*)([a-z]+)";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(UPDATE);
		String sql = "update user set name='asdf' where id='asdfsdaf'";
		Matcher matcher = pattern.matcher(sql.trim());
		int i = 0;
		while (matcher.find()) {
			System.out.println(i + ":" + matcher.group(i));
			i++;
		}

	}
}
