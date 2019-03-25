package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;

import java.sql.*;
import java.util.List;

/**
 * Abstract JDBC DAO
 *
 * @param <T>  - Identified entity
 * @param <PK> - Type primary key of entity
 */
public abstract class AbstractJdbcDao<T extends Identified<PK>, PK extends Number> implements GenericDao<T, PK> {
    protected Connection connection;


    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    protected void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException {
        prepareStatementForInsert(statement, object);
    }


    public abstract String getSelectQuery();

    public abstract String getSelectQueryForPK();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    @AutoConnection
    @Override
    public T getByPK(PK key) throws DaoException {

        try (PreparedStatement statement = this.connection.prepareStatement(getSelectQueryForPK())) {
            statement.setInt(1, (Integer) key);
            ResultSet resultSet = statement.executeQuery();
            List<T> list = parseResultSet(resultSet);
            return !list.isEmpty() ? list.get(0) : null;

        } catch (SQLException e) {
            throw new DaoException("Problem with select", e);
        }
    }

    @AutoConnection
    @Override
    public List<T> getAll() throws DaoException {

        try (PreparedStatement statement = this.connection.prepareStatement(getSelectQuery())) {
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);

        } catch (SQLException e) {
            throw new DaoException("Problem with select", e);
        }

    }

    @AutoConnection
    @Override
    public T persist(T object) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(getCreateQuery(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, object);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                PK id = (PK) new Integer(generatedKeys.getInt(1));
                object.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Problem with add entity", e);
        }
        return object;
    }

    @Override
    @AutoConnection
    public void update(T object) throws DaoException {

        try (PreparedStatement statement = this.connection.prepareStatement(getUpdateQuery())) {
            prepareStatementForUpdate(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Problem with update entity", e);
        }
    }

    @AutoConnection
    @Override
    public void delete(T object) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(getDeleteQuery())) {
            statement.setInt(1, (Integer) object.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Problem with delete entity", e);
        }
    }
}
