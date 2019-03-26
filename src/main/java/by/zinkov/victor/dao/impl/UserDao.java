package by.zinkov.victor.dao.impl;


import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.AutoConnection;
import by.zinkov.victor.dao.UserExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example User DAO implementation
 */
public class UserDao extends AbstractJdbcDao<User, Integer> implements UserExpandedDao {
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM user ";
    private static final String SELECT_USER_BY_PK_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String INSERT_NEW_USER_QUERY =
            "INSERT INTO user ( login , password , first_name,last_name , email ,phone , status_id , role_id , location ) " +
                    "VALUES ( ? , ? , ? , ? , ? , ? , ? , ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET " +
            "login = ? , password = ? , first_name = ? , last_name = ? , email = ? , phone = ? , status_id = ? , role_id = ? , location = ?" +
            "WHERE id = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE user.login = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_USER_DTO_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user JOIN user_role ON user.role_id = user_role.id JOIN user_status ON user.status_id = user_status.id WHERE user.login = ? AND user.password = ?";
    private static final String SELECT_USERS_DTO ="SELECT * FROM user JOIN user_role ON user.role_id = user_role.id JOIN user_status ON user.status_id = user_status.id ORDER BY user.id " +
            "LIMIT ?,10";


    private static final String SELECT_COUNT_USERS = "SELECT COUNT(id) FROM user";

    private static final String SELECT_COURIERS_WITH_APPROPRIATE_TRANSPORT_AND_CARGO_TYPE =
            "    SELECT user.id ,login , password , first_name,last_name , email ,phone , status_id , role_id , location , AVG(mark) from couriers.user LEFT JOIN couriers.customer_reviews ON customer_reviews.courier_id = user.id\n" +
                    "            WHERE " +
                    "    user.id IN " +
                    "            (SELECT currier_id FROM couriers.currier_capability " +
                    "                    JOIN couriers.transport_type " +
                    "                    ON currier_capability.transport_id = transport_type.id " +
                    "                    WHERE transport_type.type = ? " +
                    "                    AND " +
                    "                    currier_capability.id IN " +
                    "                    (SELECT currier_capability_id FROM couriers.supported_cargo_types " +
                    "                    JOIN couriers.cargo_types ON supported_cargo_types.type_id = cargo_types.id " +
                    "                    WHERE cargo_types.type = ?)) " +
                    "    AND  user.id NOT IN (SELECT  delivery_order.id_courier FROM couriers.delivery_order " +
                    "            JOIN couriers.order_status ON order_status.id = delivery_order.id_status " +
                    "            WHERE order_status.status = 'PERFORMED' OR order_status.status = 'ORDERED') " +
                    "    GROUP BY user.id;";








    private static final String SELECT_CLIENT_COURIERS_QUERRY =
            "SELECT DISTINCT *  FROM user JOIN delivery_order " +
                    "ON user.id = delivery_order.id_courier " +
                    "JOIN order_status ON delivery_order.id_status = order_status.id " +
                    "WHERE delivery_order.id_customer = ? AND order_status.status = 'READY'";


    @Override
    public List<User> getClientCouriers(Integer clientId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_CLIENT_COURIERS_QUERRY)) {
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Problem with select clients couriers", e);
        }
    }

    @Override
    public Map<User,Double> getCouriersWithAppropriateCargoAndTransportType(String transportType, String cargoType) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_COURIERS_WITH_APPROPRIATE_TRANSPORT_AND_CARGO_TYPE)) {
            statement.setString(1, transportType);
            statement.setString(2, cargoType);

            ResultSet resultSet = statement.executeQuery();
            return parseResultSetWithUserMark(resultSet);

        } catch (SQLException e) {
            throw new DaoException("Problem with select appropriate couriers", e);
        }
    }

    @Override
    public UserDto getDtoByPK(Integer id) throws DaoException {
        User user = getByPK(id);
        if(user == null){
            return null;
        }
        return logIn(user.getLogin(), user.getPassword());
    }


    @Override
    public User getByLogin(String login) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            List<User> list = parseResultSet(resultSet);
            return !list.isEmpty() ? list.get(0) : null;

        } catch (SQLException e) {
            throw new DaoException("Problem with select", e);
        }
    }


    @Override
    @AutoConnection
    public UserDto logIn(String login, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_DTO_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            List<User> user = parseResultSet(set);
            if (user.isEmpty()) {
                throw new DaoException("Incorrect password or login!");
            }

            UserDto userDto = new UserDto(user.get(0));
            set.previous();
            String userRole = set.getString(12);
            userDto.setUserRole(UserRole.valueOf(userRole));
            String userStatus = set.getString(14);
            userDto.setUserStatus(UserStatus.valueOf(userStatus));
            return userDto;
        } catch (SQLException e) {
            throw new DaoException("problem with logIn in dao", e);
        }
    }

    @Override
    public int countUsers() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_USERS)) {
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("problem with get all dto users in dao", e);
        }
    }

    @Override
    public List<UserDto> getAllUsersDto(int start) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USERS_DTO)) {
            statement.setInt(1,start);
            ResultSet set = statement.executeQuery();
            return parseResultSetFoUserDto(set);

        } catch (SQLException e) {
            throw new DaoException("problem with get all dto users in dao", e);
        }
    }

    private List<UserDto> parseResultSetFoUserDto(ResultSet rs) throws SQLException {
        List<UserDto> users = new ArrayList<>();
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
            user.setUserStatusId(rs.getInt(8));
            user.setUserRoleId(rs.getInt(9));

            UserDto userDto = new UserDto(user);
            String userRole = rs.getString(12);
            userDto.setUserRole(UserRole.valueOf(userRole));
            String userStatus = rs.getString(14);
            userDto.setUserStatus(UserStatus.valueOf(userStatus));
            users.add(userDto);
        }
        return users;
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws SQLException {

        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            setUserFields(user,rs);
            users.add(user);
        }
        return users;
    }


    Map<User, Double> parseResultSetWithUserMark(ResultSet rs) throws SQLException {
        Map<User, Double> users = new HashMap<>();

        while (rs.next()) {
            User user = new User();
            setUserFields(user,rs);
            users.put(user, rs.getDouble(11));
        }
        return users;
    }


    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws SQLException {
        statement.setString(1, object.getLogin());
        statement.setString(2, object.getPassword());
        statement.setString(3, object.getFirstName());
        statement.setString(4, object.getLastName());
        statement.setString(5, object.getEmail());
        statement.setString(6, object.getPhone());
        statement.setInt(7, object.getUserStatusId());
        statement.setInt(8, object.getUserRoleId());
        statement.setString(9, object.getLocation());
        if (object.getId() != null) {
            statement.setInt(10, object.getId());
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws SQLException {
        prepareStatementForInsert(statement, object);
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


    private void setUserFields(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getInt(1));
        user.setLogin(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setFirstName(rs.getString(4));
        user.setLastName(rs.getString(5));
        user.setEmail(rs.getString(6));
        user.setPhone(rs.getString(7));
        user.setUserStatusId(rs.getInt(8));
        user.setUserRoleId(rs.getInt(9));
        user.setLocation(rs.getString(10));
    }

}
