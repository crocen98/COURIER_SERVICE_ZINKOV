package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.Order;

public interface OrderExpandedDao {
    @AutoConnection
    Order getActiveOrder(Integer id) throws DaoException;

    @AutoConnection
    boolean isCourierHaveMoreThanOneActiveOrder(Integer courierId) throws DaoException;

    @AutoConnection
    Order getActiveOrderByCourierId(Integer id) throws DaoException;

    @AutoConnection
    boolean isOrderExpectedStatusMatches(Integer OrderId, Integer expectedStatusId) throws DaoException;
}
