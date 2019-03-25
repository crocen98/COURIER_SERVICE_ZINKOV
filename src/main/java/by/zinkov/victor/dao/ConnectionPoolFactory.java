package by.zinkov.victor.dao;

import by.zinkov.victor.dao.pool.ConnectionPoolImpl;

/**
 * Connection Pool Factory
 */
public class ConnectionPoolFactory {
    private static ConnectionPoolFactory instance = new ConnectionPoolFactory();

    private ConnectionPoolFactory() {
    }

    public static ConnectionPoolFactory getInstance() {
        return instance;
    }

    public ConnectionPool getConnectionPool() {
        return ConnectionPoolImpl.getInstance();
    }
}
