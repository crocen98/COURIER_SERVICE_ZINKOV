package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.OrderStatusExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.service.OrderStatusService;
import by.zinkov.victor.service.exception.ServiceException;

public class OrderStatusServiceImpl implements OrderStatusService {

    @Override
    public boolean haveActiveOrder(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            OrderStatusExpandedDao dao = (OrderStatusExpandedDao)daoFactory.getDao(OrderStatus.class);
            return dao.haveActiveOrder(id);
        } catch (DaoException e) {
            throw new ServiceException("Cannot save order!", e);
        }
    }
}
