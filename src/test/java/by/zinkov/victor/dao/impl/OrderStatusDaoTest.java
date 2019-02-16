package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.OrderStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderStatusDaoTest {
//    @Test
//    public void insertNewOrderStatus() throws DaoException {
//        GenericDao<OrderStatus,Integer> cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
//        OrderStatus cargoType =OrderStatus.CANCELED;
//        cargoType.setOrderStatus("trash");
//        Assert.assertEquals(null,cargoType.getId());
//        cargoTypeDao.persist(cargoType);
//        cargoType.setId(null);
//        cargoTypeDao.persist(cargoType);
//        Assert.assertEquals((Integer)1,cargoType.getId());
//        cargoType.setId(null);
//        cargoTypeDao.persist(cargoType);
//        Assert.assertEquals((Integer)2,cargoType.getId());
//    }
//
//
//    @Test(expected = DaoException.class)
//    public void insertExistingOrderStatusAndThrowException() throws DaoException {
//        GenericDao<OrderStatus,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
//        OrderStatus cargoType = new OrderStatus();
//        cargoType.setOrderStatus("trash");
//        Assert.assertEquals(null,cargoType.getId());
//        cargoTypeDao.persist(cargoType);
//        Assert.assertEquals((Integer) 0,cargoType.getId());
//        cargoTypeDao.persist(cargoType);
//    }
//
//    @Test
//    public void insertTwoOrderStatusAndReadTwoObject() throws DaoException {
//        GenericDao<OrderStatus,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
//        OrderStatus cargoTypeOne = new OrderStatus();
//        cargoTypeOne.setOrderStatus("trash");
//
//        OrderStatus cargoTypeTwo = new OrderStatus();
//        cargoTypeTwo.setOrderStatus("trash two");
//
//        cargoTypeDao.persist(cargoTypeOne);
//        cargoTypeDao.persist(cargoTypeTwo);
//
//        List<OrderStatus> cargoTypes = cargoTypeDao.getAll();
//        Assert.assertEquals(2,cargoTypes.size());
//        Assert.assertEquals(cargoTypeOne , cargoTypes.get(0));
//        Assert.assertEquals(cargoTypeTwo , cargoTypes.get(1));
//    }
//
//    @Test
//    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
//        GenericDao<OrderStatus,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
//        OrderStatus cargoTypeOne = new OrderStatus();
//        cargoTypeOne.setOrderStatus("trash");
//
//        OrderStatus cargoTypeTwo = new OrderStatus();
//        cargoTypeTwo.setOrderStatus("trash two");
//
//        cargoTypeDao.persist(cargoTypeOne);
//        cargoTypeDao.persist(cargoTypeTwo);
//
//
//        List<OrderStatus> cargoTypes = cargoTypeDao.getAll();
//        Assert.assertEquals(2,cargoTypes.size());
//        cargoTypeDao.delete(cargoTypeOne);
//        cargoTypes = cargoTypeDao.getAll();
//        Assert.assertEquals(1,cargoTypes.size());
//    }
//
//    @Test
//    public void insertTwoObjectAndFindByPKOne() throws DaoException {
//        GenericDao<OrderStatus,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(OrderStatus.class);
//        OrderStatus cargoTypeOne = new OrderStatus();
//        cargoTypeOne.setOrderStatus("trash");
//
//        OrderStatus cargoTypeTwo = new OrderStatus();
//        cargoTypeTwo.setOrderStatus("trash two");
//
//        cargoTypeDao.persist(cargoTypeOne);
//        cargoTypeDao.persist(cargoTypeTwo);
//
//        OrderStatus cargoTypeTest = cargoTypeDao.getByPK(0);
//        Assert.assertEquals(cargoTypeOne.getId(),cargoTypeTest.getId());
//        Assert.assertEquals(cargoTypeOne,cargoTypeTest);
//    }

}