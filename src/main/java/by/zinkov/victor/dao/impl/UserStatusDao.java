package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.UserRoleDaoExpanded;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.UserStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserStatusDao  extends AbstractJdbcDao<  UserStatus, Integer> implements GenericDao<UserStatus, Integer>  {

    private static final String SELECT_ALL_USER_STATUSES_QUERY = "SELECT * FROM user_status ";
    private static final String SELECT_USER_STATUS_BY_PK_QUERY = "SELECT * FROM user_status WHERE id = ?";



    @Override
    protected List<UserStatus> parseResultSet(ResultSet rs) throws SQLException {
        List<UserStatus> userRoles = new ArrayList<>();

            while (rs.next()) {
                String stringStatus = rs.getString(2);
                UserStatus orderStatus = UserStatus.valueOf(stringStatus);
                userRoles.add(orderStatus);
            }
            return userRoles;

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, UserStatus object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_USER_STATUSES_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_USER_STATUS_BY_PK_QUERY;
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
