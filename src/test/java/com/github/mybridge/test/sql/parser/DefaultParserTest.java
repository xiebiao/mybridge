package com.github.mybridge.test.sql.parser;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.github.mybridge.sharding.SqlType;
import com.github.mybridge.sql.parser.DefaultParser;
import com.github.mybridge.sql.parser.ParserException;
import com.github.mybridge.sql.parser.UnsupportSqlTypeException;

public class DefaultParserTest extends TestCase {

    long                        id                = 1000;
    private String              idName            = "sid";
    private String              tableName         = "user";
    private String              insertSql         = "insert    into " + tableName + " (" + idName + ",name) values("
                                                          + id + ",'xx')";
    private String              selectSql         = "select * from    " + tableName + " where " + idName + "=" + id;
    private static final String INSERT_TABLE_REGX = "INSERT (.*)";

    public void test_get_id_from_insert_sql() {
        DefaultParser dp = new DefaultParser(insertSql, idName);
        try {
            Assert.assertEquals(id, dp.getId());
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public void test_get_id_from_select_sql() {
        long id = 1000;
        DefaultParser dp = new DefaultParser(selectSql, idName);
        try {

            Assert.assertEquals(id, dp.getId());
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public void test_select_sql_type() {
        long id = 1000;
        DefaultParser dp = new DefaultParser(selectSql, idName);
        try {
            Assert.assertEquals(SqlType.READ, dp.getType());
        } catch (UnsupportSqlTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void test_get_table_name() {
        long id = 1000;
        DefaultParser dp = new DefaultParser(insertSql, idName);
        Assert.assertEquals(this.tableName, dp.getTableName());

    }

    public void test_get_table_name2() {
        long id = 1000;
        DefaultParser dp = new DefaultParser(selectSql, idName);
        Assert.assertEquals(this.tableName, dp.getTableName());

    }

    public void test_insert_sql_type() {
        long id = 1000;
        DefaultParser dp = new DefaultParser(insertSql, idName);
        try {
            Assert.assertEquals(SqlType.WRITE, dp.getType());
        } catch (UnsupportSqlTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
