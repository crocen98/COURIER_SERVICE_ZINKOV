package by.zinkov.victor.dao.factory;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.*;
import by.zinkov.victor.domain.*;

import java.lang.reflect.*;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Jdbc DAO Factory
 */
public class JdbcDaoFactory implements DaoFactory, TransactionalDaoFactory<Connection> {
    private static  JdbcDaoFactory instance = new JdbcDaoFactory();
    private Map<Class, Supplier<GenericDao>> creators = new HashMap<>();

    private class DaoInvocationHandler implements InvocationHandler {
        private GenericDao dao;

        DaoInvocationHandler(GenericDao dao) {
            this.dao = dao;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws DaoException, ConnectionPoolException {
            ConnectionPool connectionPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            Connection connection = connectionPool.retrieveConnection();

            List<Method> methods = Arrays.stream(proxy.getClass().getInterfaces()).
                    flatMap((inter) -> Arrays.
                            stream(inter.getMethods())).
                    collect(Collectors.toList());

            boolean isReturnConnectionMethod = methods.
                    stream().
                    filter(m -> m.isAnnotationPresent(AutoConnection.class))
                    .map(Method::getName)
                    .anyMatch(s -> method.getName()
                            .contains(s));
            Object result;
            try {

                if (isReturnConnectionMethod) {
                    setConnectionWithReflection(dao, connection);
                    result = method.invoke(dao, args);
                } else {
                    result = method.invoke(dao, args);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new DaoException("Problem with method " + method.getName(), e);
            } finally {
                if (isReturnConnectionMethod) {
                    connectionPool.putBackConnection(connection);
                    setConnectionWithReflection(dao, null);
                }
            }

            return result;
        }

    }

    private JdbcDaoFactory() {
        creators.put(User.class, UserDao::new);
        creators.put(UserStatus.class, UserStatusDao::new);
        creators.put(UserRole.class, UserRoleDao::new);
        creators.put(TransportType.class, TransportTypeDao::new);
        creators.put(SupportedCargoTypes.class, SupportedCargoTypesDao::new);
        creators.put(OrderStatus.class, OrderStatusDao::new);
        creators.put(Order.class, OrderDao::new);
        creators.put(CustomerReviews.class, CustomerReviewsDao::new);
        creators.put(CurrierCapability.class, CurrierCapabilityDao::new);
        creators.put(CargoType.class, CargoTypeDao::new);
        creators.put(RegistrationKey.class, RegistrationKeyDao::new);
    }

    public static JdbcDaoFactory getInstance() {
        return instance;
    }

    @Override
    public GenericDao getDao(Class entityClass) throws DaoException {
        Supplier<GenericDao> daoCreator = creators.get(entityClass);
        if (daoCreator == null) {
            throw new DaoException("Entity Class cannot be find");
        }
        GenericDao dao = daoCreator.get();
        return (GenericDao) Proxy.newProxyInstance(dao.getClass().getClassLoader(),
                dao.getClass().getInterfaces(),
                new DaoInvocationHandler(dao));
    }

    @Override
    public GenericDao getTransactionalDao(Class entityClass) throws DaoException {
        Supplier<GenericDao> daoCreator = creators.get(entityClass);
        if (daoCreator == null) {
            throw new DaoException("Entity Class cannot be find");
        }
        return daoCreator.get();
    }

    private void setConnectionWithReflection(Object dao, Connection connection) throws DaoException {
        if (!(dao instanceof AbstractJdbcDao)) {
            throw new DaoException("DAO implementation does not extend AbstractJdbcDao.");
        }

        try {
            Field connectionField = AbstractJdbcDao.class.getDeclaredField("connection");
            if (!connectionField.isAccessible()) {
                connectionField.setAccessible(true);
            }
            connectionField.set(dao, connection);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DaoException("Failed to set connection for transactional DAO. ", e);
        }
    }
}
