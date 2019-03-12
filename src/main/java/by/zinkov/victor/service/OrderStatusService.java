package by.zinkov.victor.service;

import by.zinkov.victor.domain.OrderStatus;

public interface OrderStatusService {

    boolean haveActiveOrder(Integer id) throws ServiceException;

    OrderStatus getByName(OrderStatus orderStatus) throws ServiceException;
    OrderStatus getById(Integer id) throws ServiceException;
}
