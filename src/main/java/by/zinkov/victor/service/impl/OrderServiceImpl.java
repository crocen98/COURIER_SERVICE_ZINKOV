package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.dao.impl.TransactionManager;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.dto.OrderDto;
import by.zinkov.victor.service.DistanceService;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);


    @Override
    public int getUsersOrdersCount(Integer user_id) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            OrderExpandedDao orderExpandedDao = (OrderExpandedDao) daoFactory.getDao(Order.class);
            return orderExpandedDao.getUsersOrdersCount(user_id);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get count orders!" , e);
            exception.setErrorKey("count_orders");
            throw exception;
        }
    }

    @Override
    public List<OrderDto> getAllUsersOrders(Integer page, Integer userId) throws ServiceException{
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            OrderExpandedDao orderExpandedDao = (OrderExpandedDao) daoFactory.getDao(Order.class);
            return orderExpandedDao.getAllOrdersDto(page, userId);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get all users orders!" , e);
            exception.setErrorKey("get_all_users");
            throw exception;
        }
    }

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
                    orderDao.update(activeOrder);
                    manager.commit();
                } else {
                    ServiceException exception = new ServiceException("Order already performing!");
                    exception.setErrorKey("order_already_performing");
                    throw exception;
                }
            }

        } catch (DaoException e) {
            try {
                manager.rollback();
            } catch (DaoException e1) {
                throw new RuntimeException("problem with db transactions");
            }
            ServiceException exception = new ServiceException("Problem with canceling order!" ,e);
            exception.setErrorKey("canceling_order");
            throw exception;
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

                ServiceException exception = new ServiceException("Cannot start performing order");
                exception.setErrorKey("start_performing_order");
                throw exception;
            }
            orderDao.update(order);
            LOGGER.info("COMMIT TRANSACTION");

            transactionManager.commit();
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot update order!", e);
            exception.setErrorKey("update_order");
            throw exception;
        } finally {
            try {
                LOGGER.info("END TRANSACTION");
                transactionManager.end();
            } catch (DaoException e) {
                throw new RuntimeException("problem with close transaction", e);
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
            ServiceException exception = new ServiceException("Cannot save order!", e);
            exception.setErrorKey("save_order");
            throw exception;
        }
    }

    @Override
    public Order getActiveOrderByClientId(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            OrderExpandedDao dao = (OrderExpandedDao) daoFactory.getDao(Order.class);
            return dao.getActiveOrder(id);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get active order! Client doesn't have active order!" , e);
            exception.setErrorKey("get_active_order");
            throw exception;
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
                ServiceException exception = new ServiceException("Transaction rollback, user have more than one active order!");
                exception.setErrorKey("user_have_more_than_one_active_order");
                throw exception;
            }
            transactionManager.commit();
        } catch (DaoException e) {
            try {
                transactionManager.rollback();
            } catch (DaoException e1) {
                throw new RuntimeException(e1);
            }
            ServiceException exception = new ServiceException("Cannot save order!" , e);
            exception.setErrorKey("save_order");
            throw exception;
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
