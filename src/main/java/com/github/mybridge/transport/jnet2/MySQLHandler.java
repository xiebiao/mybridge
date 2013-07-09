package com.github.mybridge.transport.jnet2;

import com.github.jnet.Handler;

public class MySQLHandler implements Handler {

    private MySQLConnection connection;

    public MySQLHandler(MySQLConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handle(byte[] data) {
        // TODO Auto-generated method stub

    }

}
