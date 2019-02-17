package by.zinkov.victor.dao.impl.pool;

import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.ConnectionPool;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of Connection Pool
 */
public class ConnectionPoolImpl implements ConnectionPool {
    private static volatile ConnectionPool instance;
    private  int poolCapacity;
    private  Queue<Connection> pool;
    private  Semaphore semaphore;
    private String url;

    private Properties properties;

    private int createdConnectionCount;
    private final Lock lock = new ReentrantLock();
    private ConnectionPoolImpl() {

              DBConfigurator configurator = new DBConfigurator();
              properties = configurator.getProperties("db.properties");
              String poolCapacityString = properties.getProperty("poolCapacity");
              poolCapacity = Integer.parseInt(poolCapacityString);
              semaphore = new Semaphore(poolCapacity);
              pool = new ArrayDeque<>(poolCapacity);
              url = properties.getProperty("url");

    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPoolImpl.class) {
                if (instance == null) {
                    instance = new ConnectionPoolImpl();
                }
            }
        }

        return instance;
    }

    @Override
    public Connection retrieveConnection() throws ConnectionPoolException {

        try {
            semaphore.acquire();
            lock.lock();
            if (createdConnectionCount < poolCapacity) {
                createdConnectionCount++;
                Connection connection = DriverManager.getConnection(url,properties);
                InvocationHandler handler = new HandlerForConnectionProxy(this, connection);
                Class[] classes = {Connection.class};
                return (Connection) Proxy.newProxyInstance(null, classes, handler);
            }
            return pool.poll();
        } catch (SQLException | InterruptedException e) {
            throw new ConnectionPoolException("error with connection",e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void putBackConnection(Connection connection) {
        try {
           // LOGGER.info("releaseConnection()");
            lock.lock();
            pool.add(connection);
        } finally {
            semaphore.release();
            lock.unlock();

        }
    }

    @Override
    public void destroyPool() throws ConnectionPoolException {

        //provide your code here

        throw new UnsupportedOperationException();
    }
}
