package by.zinkov.victor.service;

import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.dto.OrderDto;

import java.util.List;

public interface OrderService {

    void save(Order order) throws ServiceException;

    Order getActiveOrderByClientId(Integer id) throws ServiceException;

    Order getActiveOrderByCourierId(Integer id) throws ServiceException;

    void update(Order order, Integer expectedStatusId) throws ServiceException;

    void cancelOrder(Integer userId, UserRole userRole) throws ServiceException;

    int getUsersOrdersCount(Integer user_id) throws ServiceException;

    List<OrderDto> getAllUsersOrders(Integer page, Integer userId) throws ServiceException;

}
