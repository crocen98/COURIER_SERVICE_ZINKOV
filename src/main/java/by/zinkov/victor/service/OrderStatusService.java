package by.zinkov.victor.service;

import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.service.exception.ServiceException;

public interface OrderStatusService {

    boolean haveActiveOrder(Integer id) throws ServiceException;
}
