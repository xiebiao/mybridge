package com.github.mybridge.sql.parser;

import net.sf.jsqlparser.JSQLParserException;

public class ParserException extends JSQLParserException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public ParserException(String message) {
        super(message);
    }

}
