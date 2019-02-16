package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;

/**
 * Transactional DAO Factory
 * @param <T>
 */
public interface TransactionalDaoFactory<T> {
    /**
     * Get generic DAO of entity without connection
     * @param entityClass
     * @return
     * @throws DaoException should be clarify
     */
    GenericDao getTransactionalDao(Class entityClass) throws DaoException;

}
