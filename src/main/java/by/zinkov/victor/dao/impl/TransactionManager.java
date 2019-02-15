package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of transaction with DAO
 */
public final class TransactionManager {
    private Connection connection;
    TransactionManager(Connection connection){
        this.connection = connection;
    }
    public void begin() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("problem with  connection.setAutoCommit(false)" , e);
        }

    }

    public void end() throws DaoException {

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("problem with  return connection" , e);
        }
    }

    public void commit() throws DaoException{

        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("problem with  commit transaction" , e);
        }
    }

    public void rollback() throws DaoException {

        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("problem with  rollback transaction" , e);
        }
    }

}
