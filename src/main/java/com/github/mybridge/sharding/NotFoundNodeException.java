package com.github.mybridge.sharding;

/**
 * 找不到节点
 * @author xiebiao
 */
public class NotFoundNodeException extends Exception {

    private static final long serialVersionUID = -6408879663966275243L;

    public NotFoundNodeException() {

    }

    public NotFoundNodeException(String message) {
        super(message);
    }
}
