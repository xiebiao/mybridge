package com.github.mybridge.sql.parser;

/**
 * 不支持Sql类型:
 * <p>
 * Drop,Truncate,etc.
 * </p>
 * @author xiebiao
 */
public class UnsupportOperationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UnsupportOperationException(String message) {
        super(message);
    }

}
