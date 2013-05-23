package com.github.mybridge.sharding;

/**
 * 找不到分片
 * @author xiebiao
 */
public class NotFoundShardException extends Exception {

    private static final long serialVersionUID = -6408879663966275243L;

    public NotFoundShardException() {

    }

    public NotFoundShardException(String message) {
        super(message);
    }
}
