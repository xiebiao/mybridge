package mybridge2.util;

/**
 * 
 * @version 1.0 2011-6-7
 * @author zKF36895
 */
public class StringParseUtils {
	/**
	 * 把客户端传过来的sql语句解析成数据库能认识的语句(在这里我们只需要转换成我们定义的语句就好了,主要还是去掉一些不必要的空格,添加';')
	 * 
	 * @param sql
	 */
	public static String parseString(String sql) {
		StringBuffer buffer = new StringBuffer();
		String[] strings = sql.split(" ");
		// System.out.println(strings.length);
		for (int i = 0; i < strings.length; i++) {
			if ("".equals(strings[i])) {
				continue;
			}
			// 去掉子字符串中的左右空格
			String tmp = org.apache.commons.lang3.StringUtils
					.trimToEmpty(strings[i]);
			if (i != strings.length - 1) {
				buffer.append(tmp).append(" ");
			}
		}
		String middleString = org.apache.commons.lang3.StringUtils.trim(buffer
				.toString());
		String rightPad = org.apache.commons.lang3.StringUtils.trim(strings[strings.length - 1]);
		if (strings[strings.length - 1] == "") {
			middleString = middleString.concat(rightPad);
		}
		if (strings[strings.length - 1] != "") {
			middleString = middleString.concat(" ").concat(rightPad);
		}
		return sql.endsWith(";") ? middleString : middleString.concat(";");
		// return
	}

	public static void main(String[] args) {
		String sql = "/* mysql-connector-java-5.1.13 ( Revision: ${bzr.revision-id} ) */SHOW VARIABLES WHERE Variable_name ='language' OR Variable_name = 'net_write_timeout' OR Variable_name = 'interactive_timeout' OR Variable_name = 'wait_timeout' OR Variable_name = 'character_set_client' OR Variable_name = 'character_set_connection' OR Variable_name = 'character_set' OR Variable_name = 'character_set_server' OR Variable_name = 'tx_isolation' OR Variable_name = 'transaction_isolation' OR Variable_name = 'character_set_results' OR Variable_name = 'timezone' OR Variable_name = 'time_zone' OR Variable_name = 'system_time_zone' OR Variable_name = 'lower_case_table_names' OR Variable_name = 'max_allowed_packet' OR Variable_name = 'net_buffer_length' OR Variable_name = 'sql_mode' OR Variable_name = 'query_cache_type' OR Variable_name = 'query_cache_size' OR Variable_name = 'init_connect';";
		String sql1 = "/* mysql-connector-java-5.1.16 ( Revision: ${bzr.revision-id} ) */SELECT @@session.auto_increment_increment";
		String sql3 = "SHOW COLLATION;";
		String sql5 = "  select    *  from         vvv   ;";
		String test = StringParseUtils.parseString(sql1);
		String test1 = StringParseUtils.parseString(sql);
		String test3 = StringParseUtils.parseString(sql3);
		String test5 = StringParseUtils.parseString(sql5);
		System.out.println(test);
		System.out.println(test5);
		System.out.println(test1);
		System.out.println(test3);
	}
}
