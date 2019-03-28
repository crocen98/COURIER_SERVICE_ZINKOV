package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.dto.OrderDto;
import java.util.List;

public interface OrderExpandedDao extends GenericDao<Order, Integer> {
    @AutoConnection
    Order getActiveOrder(Integer id) throws DaoException;

    @AutoConnection
    boolean isCourierHaveMoreThanOneActiveOrder(Integer courierId) throws DaoException;

    @AutoConnection
    Order getActiveOrderByCourierId(Integer id) throws DaoException;

    @AutoConnection
    boolean isOrderExpectedStatusMatches(Integer OrderId, Integer expectedStatusId) throws DaoException;

    @AutoConnection
    int getUsersOrdersCount(Integer userId) throws DaoException;

    @AutoConnection
    List<OrderDto> getAllOrdersDto(Integer page, Integer userId) throws DaoException;
}
