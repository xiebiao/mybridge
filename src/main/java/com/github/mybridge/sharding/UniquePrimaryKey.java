package com.github.mybridge.sharding;

/**
 * 全局唯一Key生成策略
 * <p>
 * http://blog.csdn.net/bluishglc/article/details/7710738
 * </p>
 * @author xiebiao
 */
public interface UniquePrimaryKey {

    public long get();
}
