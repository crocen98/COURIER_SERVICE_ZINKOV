package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.AutoConnection;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.RegistrationKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationKeyDao extends AbstractJdbcDao<RegistrationKey, Integer> implements GenericDao<RegistrationKey, Integer> {
    private static final String GET_BY_USER_ID_QUERY = "SELECT * FROM registration_keys WHERE user_id = ?";
    private static final String INSERT_NEW_QUERY = "INSERT INTO registration_keys ( user_id , registration_key) VALUES ( ? , ? )";
    private static final String DELETE_QUERY = "DELETE FROM registration_keys WHERE user_id = ?";


    @AutoConnection
    @Override
    public RegistrationKey persist(RegistrationKey object) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(getCreateQuery())){
            prepareStatementForInsert(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Problem with add entity", e);
        }
        return object;
    }

    @Override
    protected List<RegistrationKey> parseResultSet(ResultSet rs) throws SQLException {
        List<RegistrationKey> registrationKeys = new ArrayList<>();
        int i = 1;
        while (rs.next()) {
            RegistrationKey registrationKey = new RegistrationKey();
            registrationKey.setId(rs.getInt(i++));
            registrationKey.setKey(rs.getString(i));
            registrationKeys.add(registrationKey);
            i = 1;
        }
        return registrationKeys;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, RegistrationKey object) throws SQLException {
        int i = 1;
        statement.setInt(i++,object.getId());
        statement.setString(i,object.getKey());
    }

    @Override
    public String getSelectQuery() {
        throw new UnsupportedOperationException("getSelectQuery() unsupported!!!");
    }

    @Override
    public String getSelectQueryForPK() {
        return GET_BY_USER_ID_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        throw new UnsupportedOperationException("getUpdateQuery() unsupported!!!");
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_QUERY;
    }


}
