package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.OrderStatusExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.service.OrderStatusService;
import by.zinkov.victor.service.ServiceException;

public class OrderStatusServiceImpl implements OrderStatusService {


    @Override
    public OrderStatus getById(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<OrderStatus, Integer> dao = (GenericDao<OrderStatus, Integer>) daoFactory.getDao(OrderStatus.class);
            return dao.getByPK(id);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get orderStatus by id!", e);
            exception.setErrorKey("get_order_status");
            throw exception;
        }
    }

    @Override
    public OrderStatus getByName(OrderStatus orderStatus) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            OrderStatusExpandedDao dao = (OrderStatusExpandedDao) daoFactory.getDao(OrderStatus.class);
            return dao.getByName(orderStatus);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get orderStatus by name!", e);
            exception.setErrorKey("get_order_status_by_name");
            throw exception;
        }
    }

    @Override
    public boolean haveActiveOrder(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            OrderStatusExpandedDao dao = (OrderStatusExpandedDao) daoFactory.getDao(OrderStatus.class);
            return dao.haveActiveOrder(id);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot save order!", e);
            exception.setErrorKey("save_order");
            throw exception;
        }
    }
}
