package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.exception.PersistException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Generic DAO
 * @param <T> - Identified entity
 * @param <PK> - Entity primary key
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /**
     * Save identified entity in DB
     * @param object identified entity
     * @return identified entity in DB
     * @throws PersistException should be clarify
     */
    @AutoConnection
    Optional<T> persist(T object) throws DaoException;

    /**
     * Get identified entity by PK
     * @param id id
     * @return identified entity
     * @throws DaoException should be clarify
     */
    @AutoConnection
    Optional<T> getByPK(PK id) throws DaoException;

    /**
     * Update identified entity
     * @param object identified entity
     * @throws PersistException should be clarify
     */
    @AutoConnection
    void update(T object) throws DaoException;

    /**
     * Delete identified entity
     * @param object identified entity
     * @throws PersistException should be clarify
     */
    @AutoConnection
    void delete(T object) throws DaoException;

    /**
     * Get all identified entity
     * @return identified entity
     * @throws DaoException should be clarify
     */
    @AutoConnection
    List<T> getAll() throws DaoException;
}