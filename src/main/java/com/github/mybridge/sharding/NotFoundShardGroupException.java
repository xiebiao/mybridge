package com.github.mybridge.sharding;

/**
 * 找不到分片
 * @author xiebiao
 */
public class NotFoundShardGroupException extends Exception {

    private static final long serialVersionUID = -6408879663966275243L;

    public NotFoundShardGroupException() {

    }

    public NotFoundShardGroupException(String message) {
        super(message);
    }
}
