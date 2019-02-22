package by.zinkov.victor.dao.pool;

import by.zinkov.victor.dao.ConnectionPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

public class HandlerForConnectionProxy implements InvocationHandler {
    private final ConnectionPool basicConnectionPool;
    private final Connection connection;

    public HandlerForConnectionProxy(ConnectionPool basicConnectionPool, Connection connection){
        this.basicConnectionPool = basicConnectionPool;
        this.connection = connection;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        final ConnectionPool connectionPool = basicConnectionPool;
        if (methodName.equals("close")) {
            connectionPool.putBackConnection((Connection) proxy);
            return null;
        } else {
            return method.invoke(connection,args);
        }
    }
}
