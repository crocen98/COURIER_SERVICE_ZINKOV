package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.OrderExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.dto.OrderDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDao extends AbstractJdbcDao<Order, Integer> implements OrderExpandedDao {

    private static final String SELECT_ALL_ORDERS_QUERY = "SELECT * FROM delivery_order ";
    private static final String SELECT_ORDER_BY_PK_QUERY = "SELECT * FROM delivery_order WHERE id = ?";
    private static final String INSERT_NEW_ORDER_QUERY =
            "INSERT INTO delivery_order ( id_customer , id_courier , price, id_status , start_point ,finish_point , description , " +
                    "start_time , finish_time , expected_time, id_cargo_type , id_transport_type ) " +
                    "VALUES ( ? , ? , ? , ? , ? , ? , ? , ?, ? , ? , ? , ?)";
    private static final String UPDATE_ORDER_QUERY = "UPDATE delivery_order SET " +
            "id_customer = ? , id_courier = ? , price = ? , id_status = ? , start_point = ? , finish_point = ? ," +
            " description = ? , start_time = ? , finish_time = ?, expected_time = ? , id_cargo_type = ? , id_transport_type = ? " +
            "WHERE id = ?";

    private static final String SELECT_USERS_ORDERS_COUNT_QUERY = "SELECT COUNT(id) FROM delivery_order WHERE id_customer = ?";

    private static final String SELECT_USERS_ORDER =
            "SELECT delivery_order.id, user.login, user.first_name, user.last_name, user.email,user.phone, " +
            "delivery_order.price, order_status.status,delivery_order.description, delivery_order.start_time, " +
            "delivery_order.finish_time, transport_type.type,cargo_types.type " +
            "FROM delivery_order JOIN user ON delivery_order.id_courier = user.id " +
            "JOIN order_status ON delivery_order.id_status = order_status.id " +
            "JOIN transport_type ON delivery_order.id_transport_type = transport_type.id " +
            "JOIN cargo_types ON delivery_order.id_cargo_type = cargo_types.id " +
            "WHERE delivery_order.id_customer = ? " +
            "ORDER BY delivery_order.id DESC " +
            "LIMIT ?,20";

    @Override
    public List<OrderDto> getAllOrdersDto(Integer page, Integer userId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USERS_ORDER)) {
            statement.setInt(1,userId);
            statement.setInt(2,page);
            ResultSet set = statement.executeQuery();
            return parseResultSetFoOrderDto(set);
        } catch (SQLException e) {
            throw new DaoException("problem with get all orderDto in dao", e);
        }
    }

    private List<OrderDto> parseResultSetFoOrderDto(ResultSet rs) throws SQLException {
        List<OrderDto> orders = new ArrayList<>();
        int i = 1;
        while (rs.next()) {
            OrderDto order = new OrderDto();
            order.setId(rs.getInt(i++));
            order.setCourierLogin(rs.getString(i++));
            order.setCourierFirstName(rs.getString(i++));
            order.setCourierLastName(rs.getString(i++));
            order.setCourierEmail(rs.getString(i++));
            order.setCourierPhone(rs.getString(i++));
            order.setPrice(rs.getBigDecimal(i++));
            order.setStatus(rs.getString(i++));
            order.setDescription(rs.getString(i++));
            order.setStartTime(rs.getTimestamp(i++));
            order.setFinishTime(rs.getTimestamp(i++));
            order.setTransportType(rs.getString(i++));
            order.setCargoType(rs.getString(i));
            orders.add(order);
            i = 1;
        }
        return orders;
    }

    @Override
    public int getUsersOrdersCount(Integer userId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USERS_ORDERS_COUNT_QUERY)) {
            statement.setInt(1,userId);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("problem with get all orders in dao", e);
        }
    }

    private static final String DELETE_USER_QUERY = "DELETE FROM delivery_order WHERE id = ?";



    private static final String SELECT_ORDER_BY_ID_WITH_STATUS = "SELECT * FROM delivery_order WHERE id = ? AND id_status = ?" ;

    private static final String SELECT_ACTIVE_ORDER_BY_USER_ID =
            "SELECT * FROM delivery_order JOIN order_status ON order_status.id = delivery_order.id_status" +
                    " WHERE delivery_order.id_customer = ? AND order_status.status = 'ORDERED'" +
                    "OR delivery_order.id_customer = ? AND order_status.status = 'PERFORMED'";


    private static final String SELECT_ACTIVE_ORDER_BY_COURIER_ID =
            "SELECT * FROM delivery_order JOIN order_status ON order_status.id = delivery_order.id_status" +
                    " WHERE delivery_order.id_courier = ? AND order_status.status = 'ORDERED'" +
                    "OR delivery_order.id_courier = ? AND order_status.status = 'PERFORMED'";


    private static final String SELECT_COUNT_OF_ACTIVE_ORDERS_IN_COURIER =
            "SELECT COUNT(*) FROM delivery_order JOIN order_status ON order_status.id = delivery_order.id_status" +
                    " WHERE delivery_order.id_courier = ? AND order_status.status = 'ORDERED'" +
                    "OR delivery_order.id_courier = ? AND order_status.status = 'PERFORMED'";



    @Override
    public boolean isOrderExpectedStatusMatches(Integer OrderId, Integer expectedStatusId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_ORDER_BY_ID_WITH_STATUS)) {
            statement.setInt(1, OrderId);
            statement.setInt(2, expectedStatusId);
            ResultSet resultSet = statement.executeQuery();
            List<Order> list = parseResultSet(resultSet);
            return !list.isEmpty();
        } catch (SQLException e) {
            throw new DaoException("Problem with select active order by user id", e);
        }
    }



    @Override
    public Order getActiveOrderByCourierId(Integer id) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_ACTIVE_ORDER_BY_COURIER_ID)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            List<Order> list = parseResultSet(resultSet);
            return !list.isEmpty() ? list.get(0) : null;
        } catch (SQLException e) {
            throw new DaoException("Problem with select active order by user id", e);
        }
    }

    @Override
    public boolean isCourierHaveMoreThanOneActiveOrder(Integer courierId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_COUNT_OF_ACTIVE_ORDERS_IN_COURIER)) {
            statement.setInt(1, courierId);
            statement.setInt(2, courierId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int countOrders = resultSet.getInt(1);
            return countOrders > 1;
        } catch (SQLException e) {
            throw new DaoException("Problem with select count of active orders!", e);
        }
    }

    @Override
    public Order getActiveOrder(Integer id) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_ACTIVE_ORDER_BY_USER_ID)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            List<Order> list = parseResultSet(resultSet);
            return !list.isEmpty() ? list.get(0) : null;
        } catch (SQLException e) {
            throw new DaoException("Problem with select", e);
        }
    }


    @Override
    protected List<Order> parseResultSet(ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();
        int i = 1;
        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt(i++));
            order.setIdCustomer(rs.getInt(i++));
            order.setIdCourier(rs.getInt(i++));
            order.setPrice(rs.getBigDecimal(i++));
            order.setIdStatus(rs.getInt(i++));
            order.setStartPoint(rs.getString(i++));
            order.setFinishPoint(rs.getString(i++));
            order.setDescription(rs.getString(i++));
            order.setStartTime(rs.getTimestamp(i++));
            order.setFinishTime(rs.getTimestamp(i++));
            order.setExpectedTime(rs.getTimestamp(i++));
            order.setIdCargoType(rs.getInt(i++));
            order.setIdTransportType(rs.getInt(i));

            orders.add(order);
            i = 1;
        }
        return orders;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Order object) throws SQLException {
        int i = 1;
        statement.setInt(i++, object.getIdCustomer());
        statement.setInt(i++, object.getIdCourier());
        statement.setBigDecimal(i++, object.getPrice());
        statement.setInt(i++, object.getIdStatus());
        statement.setString(i++, object.getStartPoint());
        statement.setString(i++, object.getFinishPoint());
        statement.setString(i++, object.getDescription());
        statement.setTimestamp(i++, object.getStartTime());
        statement.setTimestamp(i++, object.getFinishTime());
        statement.setTimestamp(i++, object.getExpectedTime());
        statement.setInt(i++, object.getIdCargoType());
        statement.setInt(i++, object.getIdTransportType());

        if (object.getId() != null) {
            statement.setInt(i, object.getId());
        }
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_ORDERS_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_ORDER_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW_ORDER_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_ORDER_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_USER_QUERY;
    }
}
