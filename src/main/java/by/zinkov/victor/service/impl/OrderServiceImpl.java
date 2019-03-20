package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.dao.impl.TransactionManager;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.service.DistanceService;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public void cancelOrder(Integer userId, UserRole userRole) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        TransactionManager manager = new TransactionManager();
        try {
            OrderStatusExpandedDao statusDao = (OrderStatusExpandedDao) ((JdbcDaoFactory) daoFactory).getTransactionalDao(OrderStatus.class);
            OrderExpandedDao orderDao = (OrderExpandedDao) ((JdbcDaoFactory) daoFactory).getTransactionalDao(Order.class);

            manager.begin((AbstractJdbcDao) statusDao, (AbstractJdbcDao) orderDao);
            Order activeOrder;
            if (userRole == UserRole.CLIENT) {
                activeOrder = orderDao.getActiveOrder(userId);
            } else {
                activeOrder = orderDao.getActiveOrderByCourierId(userId);
            }

            if (activeOrder != null) {
                OrderStatus orderStatus = statusDao.getByName(OrderStatus.ORDERED);
                if (orderStatus.getId().equals(activeOrder.getIdStatus())) {
                    OrderStatus neededOrderStatus = statusDao.getByName(OrderStatus.CANCELED);
                    activeOrder.setIdStatus(neededOrderStatus.getId());
                    ((GenericDao<Order, Integer>) orderDao).update(activeOrder);
                    manager.commit();
                } else {
                    throw new ServiceException("already_perfom.error");
                }
            }

        } catch (DaoException e) {
            try {
                manager.rollback();
            } catch (DaoException e1) {
                throw new RuntimeException("problem with db transactions");
            }
            throw new ServiceException("error.key",e);
        } finally {
            try {
                manager.end();
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void update(Order order, Integer expectedStatusId) throws ServiceException {
        LOGGER.info("START TRANSACTION METHOD UPDDATE()");
        LOGGER.info(order + " " + "expectedStatusId: " + expectedStatusId);


        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderExpandedDao orderDao = (OrderExpandedDao) ((JdbcDaoFactory) daoFactory).getTransactionalDao(Order.class);
            transactionManager.begin((AbstractJdbcDao) orderDao);
            if (!orderDao.isOrderExpectedStatusMatches(order.getId(), expectedStatusId)) {
                LOGGER.info("ROLLBACK TRANSACTION");
                transactionManager.rollback();
                throw new ServiceException("cannot start perfoming order");
            }
            ((GenericDao<Order, Integer>) orderDao).update(order);
            LOGGER.info("COMMIT TRANSACTION");

            transactionManager.commit();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Cannot update order!", e);
        } finally {
            try {
                LOGGER.info("END TRANSACTION");
                transactionManager.end();
            } catch (DaoException e) {
                throw new ServiceException("problem with close transaction", e);
            }
        }
    }

    @Override
    public Order getActiveOrderByCourierId(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            OrderExpandedDao dao = (OrderExpandedDao) daoFactory.getDao(Order.class);
            return dao.getActiveOrderByCourierId(id);
        } catch (DaoException e) {
            throw new ServiceException("Cannot save order!", e);
        }
    }

    @Override
    public Order getActiveOrderByClientId(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            OrderExpandedDao dao = (OrderExpandedDao) daoFactory.getDao(Order.class);
            return dao.getActiveOrder(id);
        } catch (DaoException e) {
            throw new ServiceException("Cannot get active order!", e);
        }
    }

    @Override
    public void save(Order order) throws ServiceException {
        JdbcDaoFactory daoFactory = JdbcDaoFactory.getInstance();
        TransactionManager transactionManager = new TransactionManager();
        try {
            GenericDao<Order, Integer> orderDao = daoFactory.getTransactionalDao(Order.class);
            GenericDao<User, Integer> userDao = daoFactory.getTransactionalDao(User.class);
            transactionManager.begin((AbstractJdbcDao) orderDao, (AbstractJdbcDao) userDao);
            orderDao.persist(order);
            User courier = userDao.getByPK(order.getIdCourier());
            if (((OrderExpandedDao) orderDao).isCourierHaveMoreThanOneActiveOrder(order.getIdCourier())
                    || calculateDistance(courier.getLocation(), order.getStartPoint()) > 30) {
                LOGGER.info("Start rollback transaction");
                transactionManager.rollback();
                LOGGER.info("Finish rollback transaction");
                throw new ServiceException("Transaction rollback, user have more than one active order!");
            }
            transactionManager.commit();

        } catch (DaoException e) {
            try {
                transactionManager.rollback();
            } catch (DaoException e1) {
                e1.printStackTrace();
            }
            throw new ServiceException("Cannot save order!", e);
        } finally {
            try {
                LOGGER.info("Start end() transaction");
                transactionManager.end();
                LOGGER.info("Finish end() transaction");
            } catch (DaoException e) {
                LOGGER.info("Error end() transaction");
                throw new RuntimeException("Cannot end() transaction", e);
            }
        }
    }


    private double calculateDistance(String order, String courier) {
        DistanceService service = new DistanceService();
        return service.calculate(order, courier);
    }
}
