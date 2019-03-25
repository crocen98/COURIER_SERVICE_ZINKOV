package by.zinkov.victor.dao;

import by.zinkov.victor.dao.factory.JdbcDaoFactory;

/**
 * Factory producer
 * Provide DAO Factory by type
 */
public class FactoryProducer {
    private static FactoryProducer instance = new FactoryProducer();

    private FactoryProducer() {
    }

    public FactoryProducer getInstance() {
        return instance;
    }

    public static DaoFactory getDaoFactory(DaoFactoryType type) {
        switch (type) {
            case JDBC:
                return JdbcDaoFactory.getInstance();
            default:
                throw new UnsupportedOperationException();

        }
    }
}
