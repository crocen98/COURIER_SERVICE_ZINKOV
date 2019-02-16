package by.zinkov.victor.dao.impl;


import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Example User DAO implementation
 */
public class UserDao extends AbstractJdbcDao<User, Integer> implements GenericDao<User, Integer> {
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM user " +
            " INNER JOIN  user_status ON user.status_id = user_status.id  INNER JOIN user_role ON user_role.id = user.role_id";
    private static final String SELECT_USER_BY_PK_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String INSERT_NEW_USER_QUERY =
            "INSERT INTO user ( login , password , first_name,last_name , email ,phone , status_id , role_id , location ) " +
                    "VALUES ( ? , ? , ? , ? , ? , ? , ? , ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET " +
            "login = ? , password = ? , first_name = ? , last_name = ? , email = ? , phone = ? , status_id = ? , role_id = ? , location = ?" +
            "WHERE id = ?";

    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id = ?";






    @Override
    protected List<User> parseResultSet(ResultSet rs) throws SQLException {

        List<User> users = new ArrayList<>();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setPhone(rs.getString(7));
                user.setLocation(rs.getString(10));
                user.setUserStatus(UserStatus.valueOf(rs.getString(12)));
                user.setUserRole(UserRole.valueOf(rs.getString(14)));
                users.add(user);
            }
            return users;
    }



    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws SQLException {

            statement.setString(1,object.getLogin());
            statement.setString(2,object.getPassword());
            statement.setString(3,object.getFirstName());
            statement.setString(4,object.getLastName());
            statement.setString(5,object.getEmail());
            statement.setString(6,object.getPhone());
            statement.setInt(7,object.getUserStatus().getId());
            statement.setInt(8,object.getUserRole().getId());
            System.out.println(object.getLocation());
            statement.setString(9,object.getLocation());
            if(object.getId() != 0){
                statement.setInt(10, object.getId());
            }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws SQLException {
        prepareStatementForInsert(statement,object);
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_USERS_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_USER_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {

        return INSERT_NEW_USER_QUERY;
    }

    @Override
    public String getUpdateQuery() {

        return UPDATE_USER_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_USER_QUERY;
    }


//    @Override
//    public Optional<User> create() throws PersistException {
//        //provide your code here
//
//        throw new UnsupportedOperationException();
//    }


}
