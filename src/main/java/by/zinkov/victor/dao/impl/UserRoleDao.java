package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.UserRoleExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao extends AbstractJdbcDao<UserRole, Integer> implements GenericDao<UserRole, Integer>, UserRoleExpandedDao {
    private static final String SELECT_ALL_USER_ROLES_QUERY = "SELECT * FROM user_role ";
    private static final String SELECT_USER_ROLE_BY_PK_QUERY = "SELECT * FROM user_role WHERE id = ?";
    private static final String SELECT_USER_ROLE_BY_NAME = "SELECT * FROM user_role WHERE role = ?";


    @Override
    public UserRole getByName(String name) throws DaoException{
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_ROLE_BY_NAME)) {
            ResultSet set = statement.executeQuery();

                UserRole role = UserRole.valueOf(set.getString(2));
                role.setId(set.getInt(1));

                return role;

        } catch (SQLException e) {
            throw new DaoException("Cannot find by nme user role",e);
        }
    }

    @Override
    protected List<UserRole> parseResultSet(ResultSet rs) throws SQLException {
        List<UserRole> userRoles = new ArrayList<>();

        while (rs.next()) {
            String stringStatus = rs.getString(2);
            UserRole orderStatus = UserRole.valueOf(stringStatus);
            userRoles.add(orderStatus);
        }
        return userRoles;

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, UserRole object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_USER_ROLES_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_USER_ROLE_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUpdateQuery() {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getDeleteQuery() {
        throw new UnsupportedOperationException();
    }
}
