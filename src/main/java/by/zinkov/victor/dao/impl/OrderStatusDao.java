package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.PersistException;
import by.zinkov.victor.domain.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDao extends AbstractJdbcDao< OrderStatus, Integer> implements GenericDao<OrderStatus, Integer> {

    private static final String SELECT_ALL_ORDERS_STATUS_QUERY = "SELECT * FROM order_status ";
    private static final String SELECT_ORDER_STATUS_BY_PK_QUERY = "SELECT * FROM order_status WHERE id = ?";


    @Override
    protected List<OrderStatus> parseResultSet(ResultSet rs) throws SQLException {
        List<OrderStatus> statuses = new ArrayList<>();

            while (rs.next()) {
                String stringStatus = rs.getString(2);
                OrderStatus orderStatus = OrderStatus.valueOf(stringStatus);
                statuses.add(orderStatus);
            }
            return statuses;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, OrderStatus object)  {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_ORDERS_STATUS_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_ORDER_STATUS_BY_PK_QUERY;
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
