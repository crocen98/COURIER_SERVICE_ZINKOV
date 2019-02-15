package by.zinkov.victor.dao;

import by.zinkov.victor.dao.impl.pool.ConnectionPoolImpl;

/**
 * Connection Pool Factory
 */
public class ConnectionPoolFactory {
    private static volatile ConnectionPoolFactory instance;

    private ConnectionPoolFactory() {}

    public static ConnectionPoolFactory getInstance() {
        if (instance == null) {
            synchronized (ConnectionPoolFactory.class) {
                if (instance == null) {
                    instance = new ConnectionPoolFactory();
                }
            }
        }

        return instance;
    }

    public ConnectionPool getConnectionPool() {
        return ConnectionPoolImpl.getInstance();
    }
}
