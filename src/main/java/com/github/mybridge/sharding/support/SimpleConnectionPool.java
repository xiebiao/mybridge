package com.github.mybridge.sharding.support;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.mybridge.sharding.ConnectionPool;

public class SimpleConnectionPool implements ConnectionPool {

    protected static DruidDataSource ds;

    public SimpleConnectionPool() {
        try {
            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            ds = new DruidDataSource();
            ds.setDriver(driver);
            ds.setUrl("jdbc:mysql://127.0.0.1:3306/mybridge");
            ds.setUsername("root");
            ds.setPassword("wangzhu");
            ds.setTestOnBorrow(false);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
