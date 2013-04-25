package com.github.mybridge.sharding;

/**
 * 读写状态
 * <p>
 * 实现类可能存在三种状态：
 * <ul>
 * <li>1. 只可写</li>
 * <li>2. 只可读</li>
 * <li>3. 可读写</li>
 * </ul>
 * </p>
 * 
 * @author xiebiao
 */
public interface State {
	/**
	 * 可写入
	 * 
	 * @return true:可写,false:不可写
	 */
	public boolean canWrite();

	/**
	 * 可读取
	 * 
	 * @return true:可读,false:不可读
	 */
	public boolean canRead();
}
