package by.zinkov.victor.service;

import by.zinkov.victor.domain.Order;

public interface OrderService {

    void save(Order order) throws ServiceException;

    Order getActiveOrderByClientId(Integer id) throws ServiceException;

    Order getActiveOrderByCourierId(Integer id) throws ServiceException;

    void update(Order order, Integer expectedStatusId) throws ServiceException;


}
