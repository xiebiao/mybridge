package com.github.mybridge.sql.parser;

import com.github.mybridge.sharding.SqlType;

public interface Parser {

    /**
     * 分表id
     * @return
     * @throws ParserException
     */
    public long getId() throws ParserException;

    /**
     * 获取表名称
     * @return
     */
    public String getTableName();

    /**
     * 替换表名称,返回替换表名后的sql
     * @param tableName
     * @return
     * @throws ParserException
     */
    public String replace(String tableName);

    public SqlType getType() throws UnsupportSqlTypeException;
}
