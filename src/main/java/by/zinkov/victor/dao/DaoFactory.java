package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;

/**
 * Dao Factory
 */
public interface DaoFactory {
    /**
     * Return implementation of DAO for entity class
     * @param entityClass - entity class
     * @return - implementation of DAO for entity class
     * @throws DaoException - should be clarify
     */
    GenericDao getDao(Class entityClass) throws DaoException;
}
