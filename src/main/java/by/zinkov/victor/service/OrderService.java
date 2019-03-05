package by.zinkov.victor.service;
import by.zinkov.victor.domain.Order;

import javax.servlet.http.HttpServletRequest;

public interface OrderService  {
     Order createOrderFirstStage(HttpServletRequest request , String transportTypeString, String cargoTypeString) throws ServiceException;
     void save(Order order) throws ServiceException;
     Order getActiveOrderByClientId(Integer id) throws ServiceException;

}
