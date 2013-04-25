package com.github.mybridge.sharding;


/**
 * 读写状态
 * 
 * @author xiebiao
 */
public interface State {
	/**
	 * 可写入
	 * 
	 * @param node
	 * @return
	 */
	public boolean canWrite();

	/**
	 * 可读取
	 * 
	 * @param node
	 * @return
	 */
	public boolean canRead();
}
