package by.zinkov.victor.service;

public interface OrderStatusService {

    boolean haveActiveOrder(Integer id) throws ServiceException;
}
