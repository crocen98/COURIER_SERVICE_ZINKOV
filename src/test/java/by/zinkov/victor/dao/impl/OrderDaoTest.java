package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.Order;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


public class OrderDaoTest extends AbstractDaoTest {

    @Test
    public void insertNewOrder() throws DaoException {
        GenericDao<Order,Integer> orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
        Order order = new Order();
        order.setFinish_time(Date.valueOf("23.12.2019"));
        order.setExpected_time(Date.valueOf("23.12.2019"));
        order.setStart_time(Date.valueOf("23.12.2019"));
        order.setDescription(" aaa bbb ccc");
        order.setFinish_point("point B");
        order.setStart_point("Point A");
        order.setId_status(1);
        order.setPrice(new BigDecimal(12.4));
        order.setIdCourier(1);
        order.setIdCustomer(1);

        Assert.assertEquals(null,order.getId());
        orderDao.persist(order);
        order.setId(null);
        orderDao.persist(order);
        Assert.assertEquals((Integer)1,order.getId());
        order.setId(null);
        orderDao.persist(order);
        Assert.assertEquals((Integer)2,order.getId());
    }


    @Test(expected = DaoException.class)
    public void insertExistingOrderAndThrowException() throws DaoException {
        GenericDao<Order,Integer>  orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
        Order order = new Order();
        order.setFinish_time(Date.valueOf("23.12.2019"));
        order.setExpected_time(Date.valueOf("23.12.2019"));
        order.setStart_time(Date.valueOf("23.12.2019"));
        order.setDescription(" aaa bbb ccc");
        order.setFinish_point("point B");
        order.setStart_point("Point A");
        order.setId_status(1);
        order.setPrice(new BigDecimal(12.4));
        order.setIdCourier(1);
        order.setIdCustomer(1);
        Assert.assertEquals(null,order.getId());
        orderDao.persist(order);
        Assert.assertEquals((Integer) 0,order.getId());
        orderDao.persist(order);
    }

    @Test
    public void insertTwoOrderAndReadTwoObject() throws DaoException {
        GenericDao<Order,Integer>  orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
        Order orderOne = new Order();
        orderOne.setFinish_time(Date.valueOf("23.12.2019"));
        orderOne.setExpected_time(Date.valueOf("23.12.2019"));
        orderOne.setStart_time(Date.valueOf("23.12.2019"));
        orderOne.setDescription(" aaa bbb ccc");
        orderOne.setFinish_point("point B");
        orderOne.setStart_point("Point A");
        orderOne.setId_status(1);
        orderOne.setPrice(new BigDecimal(12.4));
        orderOne.setIdCourier(1);
        orderOne.setIdCustomer(1);

        Order orderTwo = new Order();
        orderTwo.setFinish_time(Date.valueOf("23.12.2019"));
        orderTwo.setExpected_time(Date.valueOf("23.12.2019"));
        orderTwo.setStart_time(Date.valueOf("23.12.2019"));
        orderTwo.setDescription(" aaa bbb ccc");
        orderTwo.setFinish_point("point B");
        orderTwo.setStart_point("Point A");
        orderTwo.setId_status(1);
        orderTwo.setPrice(new BigDecimal(12.4));
        orderTwo.setIdCourier(1);
        orderTwo.setIdCustomer(1);


        orderDao.persist(orderOne);
        orderDao.persist(orderTwo);

        List<Order> orders = orderDao.getAll();
        Assert.assertEquals(2,orders.size());
        Assert.assertEquals(orderOne , orders.get(0));
        Assert.assertEquals(orderTwo , orders.get(1));
    }

    @Test
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<Order,Integer>  orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
        Order orderOne = new Order();
        orderOne.setFinish_time(Date.valueOf("23.12.2019"));
        orderOne.setExpected_time(Date.valueOf("23.12.2019"));
        orderOne.setStart_time(Date.valueOf("23.12.2019"));
        orderOne.setDescription(" aaa bbb ccc");
        orderOne.setFinish_point("point B");
        orderOne.setStart_point("Point A");
        orderOne.setId_status(1);
        orderOne.setPrice(new BigDecimal(12.4));
        orderOne.setIdCourier(1);
        orderOne.setIdCustomer(1);

        Order orderTwo = new Order();
        orderTwo.setFinish_time(Date.valueOf("23.12.2019"));
        orderTwo.setExpected_time(Date.valueOf("23.12.2019"));
        orderTwo.setStart_time(Date.valueOf("23.12.2019"));
        orderTwo.setDescription(" aaa bbb ccc");
        orderTwo.setFinish_point("point B");
        orderTwo.setStart_point("Point A");
        orderTwo.setId_status(1);
        orderTwo.setPrice(new BigDecimal(12.4));
        orderTwo.setIdCourier(1);
        orderTwo.setIdCustomer(1);

        orderDao.persist(orderOne);
        orderDao.persist(orderTwo);


        List<Order> orders = orderDao.getAll();
        Assert.assertEquals(2,orders.size());
        orderDao.delete(orderOne);
        orders = orderDao.getAll();
        Assert.assertEquals(1,orders.size());
    }

    @Test
    public void insertTwoObjectAndFindByPKOne() throws DaoException {
        GenericDao<Order,Integer>  orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
        Order orderOne = new Order();
        orderOne.setFinish_time(Date.valueOf("23.12.2019"));
        orderOne.setExpected_time(Date.valueOf("23.12.2019"));
        orderOne.setStart_time(Date.valueOf("23.12.2019"));
        orderOne.setDescription(" aaa bbb ccc");
        orderOne.setFinish_point("point B");
        orderOne.setStart_point("Point A");
        orderOne.setId_status(1);
        orderOne.setPrice(new BigDecimal(12.4));
        orderOne.setIdCourier(1);
        orderOne.setIdCustomer(1);

        Order orderTwo = new Order();
        orderTwo.setFinish_time(Date.valueOf("23.12.2019"));
        orderTwo.setExpected_time(Date.valueOf("23.12.2019"));
        orderTwo.setStart_time(Date.valueOf("23.12.2019"));
        orderTwo.setDescription(" aaa bbb ccc");
        orderTwo.setFinish_point("point B");
        orderTwo.setStart_point("Point A");
        orderTwo.setId_status(1);
        orderTwo.setPrice(new BigDecimal(12.4));
        orderTwo.setIdCourier(1);
        orderTwo.setIdCustomer(1);

        orderDao.persist(orderOne);
        orderDao.persist(orderTwo);

        Order orderTest = orderDao.getByPK(0);
        Assert.assertEquals(orderOne.getId(),orderTest.getId());
        Assert.assertEquals(orderOne,orderTest);
    }

}