package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.OrderStatus;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class OrderStatusDaoTest extends AbstractDaoTest {
    @Test(expected = UnsupportedOperationException.class)
    public void insertNewOrderStatus() throws DaoException {
        GenericDao<OrderStatus, Integer> cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
        OrderStatus cargoType = OrderStatus.CANCELED;
        cargoType.setId(cargoType.getId() - 1);
        cargoTypeDao.persist(cargoType);
    }


    @Test
    public void readTwoObject() throws DaoException {
        GenericDao<OrderStatus, Integer> cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
        List<OrderStatus> cargoTypes = cargoTypeDao.getAll();
        Assert.assertEquals(4, cargoTypes.size());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<OrderStatus, Integer> cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
        OrderStatus status = OrderStatus.ORDERED;
        status.setId(status.getId() - 1);
        cargoTypeDao.delete(status);
    }


}