package com.github.mybridge.engine;

import java.util.List;

import com.alibaba.druid.sql.parser.ParserException;

/**
 * 解析器
 * 
 * @author xiebiao
 */
public interface Parser {

	/**
	 * 是否写操作<br/>
	 * <ul>
	 * <li>INSERT,UPDATE,DELETE为写操作</li>
	 * <li>SELECT为读操作</li>
	 * </ul>
	 * 
	 * @return
	 */
	boolean isWrite();

	List<String> getFileds() throws ParserException;

	List<String> getTables() throws ParserException;

	/**
	 * 分库分表业务id
	 * 
	 * @return
	 * @throws ParserException
	 */
	String getId() throws ParserException;

}
