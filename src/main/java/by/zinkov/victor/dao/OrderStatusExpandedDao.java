package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.OrderStatus;

public interface OrderStatusExpandedDao {
    @AutoConnection
    boolean haveActiveOrder(Integer id) throws DaoException;

    @AutoConnection
    OrderStatus getByName(OrderStatus orderStatus) throws DaoException;

}
