package by.zinkov.victor.dao.impl;
import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDao extends AbstractJdbcDao<Order, Integer> implements GenericDao<Order, Integer> {

    private static final String SELECT_ALL_ORDERS_QUERY = "SELECT * FROM delivery_order ";
    private static final String SELECT__ORDER_BY_PK_QUERY = "SELECT * FROM delivery_order WHERE id = ?";
    private static final String INSERT_NEW__ORDER_QUERY =
            "INSERT INTO delivery_order ( id_customer , id_courier , price, id_status , start_point ,finish_point , description , start_time , finish_time , expected_time ) " +
                    "VALUES ( ? , ? , ? , ? , ? , ? , ? , ?, ? , ?)";
    private static final String UPDATE__ORDER_QUERY = "UPDATE delivery_order SET " +
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
                order.setId_status(rs.getInt(i++));
                order.setStart_point(rs.getString(i++));
                order.setFinish_point(rs.getString(i++));
                order.setDescription(rs.getString(i++));
                order.setStart_time(rs.getDate(i++));
                order.setFinish_time(rs.getDate(i++));
                order.setExpected_time(rs.getDate(i++));
                orders.add(order);
            }
            return orders;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Order object) throws SQLException {
            int i = 1;
            statement.setInt(i++,object.getIdCustomer());
            statement.setInt(i++,object.getIdCourier());
            statement.setBigDecimal(i++,object.getPrice());
            statement.setInt(i++,object.getId_status());
            statement.setString(i++,object.getStart_point());
            statement.setString(i++,object.getFinish_point());
            statement.setString(i++,object.getDescription());
            statement.setDate(i++,(Date) object.getStart_time());
            statement.setDate(i++,(Date) object.getFinish_time());
            statement.setDate(i++,(Date) object.getExpected_time());
            if(object.getId() != 0){
                statement.setInt(i++, object.getId());
            }
            System.out.println("WORCK ");
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_ORDERS_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT__ORDER_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW__ORDER_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE__ORDER_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_USER_QUERY;
    }
}
