package com.github.mybridge.sql.parser;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DefaultParserTest extends TestCase {

    long           id     = 1000;
    private String idName = "sid";

    public void test_insert() {
        String sql = "insert into user (" + idName + ",name) values(" + id + ",'xx')";
        DefaultParser dp = new DefaultParser(sql);
        try {
            dp.setIdName(idName);
            Assert.assertEquals(id, dp.getId());
        } catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void test_select_0() {
        long id = 1000;
        String sql = "select * from user where " + idName + "=" + id;
        DefaultParser dp = new DefaultParser(sql);
        try {
            dp.setIdName(idName);
            Assert.assertEquals(id, dp.getId());
        } catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void no_test_select_1() {
        long id = 1000;
        String sql = "select * from user where name='xiebiao' and id=" + id;
        DefaultParser dp = new DefaultParser(sql);
        try {
            dp.setIdName("id");
            Assert.assertEquals(id, dp.getId());
        } catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
