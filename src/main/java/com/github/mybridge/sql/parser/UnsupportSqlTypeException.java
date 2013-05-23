package com.github.mybridge.sql.parser;

/**
 * 不支持Sql类型:
 * <p>
 * Drop,Truncate,etc.
 * </p>
 * @author xiebiao
 */
public class UnsupportSqlTypeException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UnsupportSqlTypeException(String message) {
        super(message);
    }

}
