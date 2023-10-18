package com.maximyasn.core.repo;


import java.sql.Connection;


public abstract class AbstractRepo<T> implements RepoInterface<T> {

    private Connection connection;

    public AbstractRepo(Connection connection) {
        this.connection = connection;
    }


    public Connection getConnection() {
        return connection;
    }
}
