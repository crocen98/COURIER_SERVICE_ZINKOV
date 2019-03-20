package by.zinkov.victor.service;

import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.UserRole;

public interface OrderService {

    void save(Order order) throws ServiceException;

    Order getActiveOrderByClientId(Integer id) throws ServiceException;

    Order getActiveOrderByCourierId(Integer id) throws ServiceException;

    void update(Order order, Integer expectedStatusId) throws ServiceException;

    void cancelOrder(Integer userId, UserRole userRole) throws ServiceException;

}
