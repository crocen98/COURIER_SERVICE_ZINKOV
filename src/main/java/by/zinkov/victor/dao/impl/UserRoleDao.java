package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.PersistException;
import by.zinkov.victor.domain.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao  extends AbstractJdbcDao<UserRole, Integer> implements GenericDao<UserRole, Integer> {
    private static final String SELECT_ALL_USER_ROLES_QUERY = "SELECT * FROM user_role ";
    private static final String SELECT__USER_ROLE_BY_PK_QUERY = "SELECT * FROM user_role WHERE id = ?";

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
    protected void prepareStatementForInsert(PreparedStatement statement, UserRole object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_USER_ROLES_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT__USER_ROLE_BY_PK_QUERY;
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
