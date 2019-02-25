package by.zinkov.victor.dao.impl;
import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDao extends AbstractJdbcDao<Order, Integer> implements GenericDao<Order, Integer> {

    private static final String SELECT_ALL_ORDERS_QUERY = "SELECT * FROM delivery_order ";
    private static final String SELECT_ORDER_BY_PK_QUERY = "SELECT * FROM delivery_order WHERE id = ?";
    private static final String INSERT_NEW_ORDER_QUERY =
            "INSERT INTO delivery_order ( id_customer , id_courier , price, id_status , start_point ,finish_point , description , start_time , finish_time , expected_time ) " +
                    "VALUES ( ? , ? , ? , ? , ? , ? , ? , ?, ? , ?)";
    private static final String UPDATE_ORDER_QUERY = "UPDATE delivery_order SET " +
            "id_customer = ? , id_courier = ? , price = ? , id_status = ? , start_point = ? , finish_point = ? , description = ? , start_time = ? , finish_time = ?, expected_time = ?" +
            "WHERE id = ?";

    private static final String DELETE_USER_QUERY = "DELETE FROM delivery_order WHERE id = ?";

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
                order.setExpectedTime(rs.getTimestamp(i));
                orders.add(order);
                i = 1;
            }
            return orders;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Order object) throws SQLException {
            int i = 1;
            statement.setInt(i++,object.getIdCustomer());
            statement.setInt(i++,object.getIdCourier());
            statement.setBigDecimal(i++,object.getPrice());
            statement.setInt(i++,object.getIdStatus());
            statement.setString(i++,object.getStartPoint());
            statement.setString(i++,object.getFinishPoint());
            statement.setString(i++,object.getDescription());
            statement.setTimestamp(i++,(Timestamp) object.getStartTime());
            statement.setTimestamp(i++,(Timestamp) object.getFinishTime());
            statement.setTimestamp(i++,(Timestamp) object.getExpectedTime());
            if(object.getId() != null){
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
