package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.ConnectionPool;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.pool.ConnectionPoolImpl;
import by.zinkov.victor.util.SetterObjectField;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of transaction with DAO
 */
public final class TransactionManager {
    private Connection connection;

    public void begin(GenericDao dao, GenericDao ... daos) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPoolImpl.getInstance();
            this.connection = pool.retrieveConnection();
            connection.setAutoCommit(false);
            SetterObjectField setter = new SetterObjectField();

            setter.setField(dao,"connection",connection);

            for (GenericDao specificDao : daos){
                setter.setField(specificDao,"connection",connection);
            }

        } catch (SQLException| ConnectionPoolException e) {
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
