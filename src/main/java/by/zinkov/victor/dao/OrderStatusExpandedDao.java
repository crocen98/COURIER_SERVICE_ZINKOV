package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;

public interface OrderStatusExpandedDao {
    @AutoConnection
    boolean haveActiveOrder(Integer id) throws DaoException;
}
