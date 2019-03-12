package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.List;


public class OrderDaoTest extends AbstractDaoTest {

    @Test
    public void insertNewOrder() throws DaoException {
//
//        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
//        //User user =  User.builder().build();
//        User user = new User();
//
//        user.setLocation("trash");
//        user.setLogin("crocen98");
//        user.setLastName("Zinkov");
//        user.setFirstName("Victor");
//        user.setEmail("s@icloud.com");
//        user.setPhone("+35291052630");
//        user.setUserStatusId(1);
//        user.setUserRoleId(2);
//        user.setPassword("122234234512223423451222342345122234234545555");
//        userDao.persist(user);
//
//        GenericDao<Order,Integer> orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
//        Order order = new Order();
//        order.setFinishTime(new Timestamp(66666653255555L));
//        order.setExpectedTime(new Timestamp(66666653259999L));
//        order.setStartTime(new Timestamp(6666665323000L));
//        order.setDescription(" aaa bbb ccc");
//        order.setFinishPoint("point B");
//        order.setStartPoint("Point A");
//        order.setIdStatus(1);
//        order.setPrice(new BigDecimal(12.4));
//        order.setIdCourier(0);
//        order.setIdCustomer(0);
//
//        Assert.assertEquals(null,order.getId());
//        orderDao.persist(order);
//        order.setId(null);
//        orderDao.persist(order);
//        Assert.assertEquals((Integer)1,order.getId());
//        order.setId(null);
//        orderDao.persist(order);
//        Assert.assertEquals((Integer)2,order.getId());
    }


    @Test(expected = DaoException.class)
    public void insertExistingOrderAndThrowException() throws DaoException {

        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
//        User user =  User.builder().build();
        User user = new User();

        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatusId(1);
        user.setUserRoleId(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);


        GenericDao<Order,Integer>  orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
        Order order = new Order();
        order.setFinishTime(new Timestamp(66666653255555L));
        order.setExpectedTime(new Timestamp(66666653259999L));
        order.setStartTime(new Timestamp(6666665323000L));
        order.setDescription(" aaa bbb ccc");
        order.setFinishPoint("point B");
        order.setStartPoint("Point A");
        order.setIdStatus(1);
        order.setPrice(new BigDecimal(12.40).round(new MathContext(2)));
        order.setIdCourier(0);
        order.setIdCustomer(0);
        Assert.assertEquals(null,order.getId());
        orderDao.persist(order);
        Assert.assertEquals((Integer) 0,order.getId());
        orderDao.persist(order);
    }

    @Test
    public void insertTwoOrderAndReadTwoObject() throws DaoException {
//        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
////        User user =  User.builder().build();
//
//        User user = new User();
//        user.setLocation("trash");
//        user.setLogin("crocen98");
//        user.setLastName("Zinkov");
//        user.setFirstName("Victor");
//        user.setEmail("s@icloud.com");
//        user.setPhone("+35291052630");
//        user.setUserStatusId(1);
//        user.setUserRoleId(2);
//        user.setPassword("122234234512223423451222342345122234234545555");
//        userDao.persist(user);
//
////        User user2 =  User.builder().build();
//        User user2 = new User();
//
//        user2.setLocation("trash");
//        user2.setLogin("crocen978");
//        user2.setLastName("Zinkov");
//        user2.setFirstName("Victor");
//        user2.setEmail("s@icloudf.com");
//        user2.setPhone("+35291052630");
//        user2.setUserStatusId(1);
//        user2.setUserRoleId(2);
//        user2.setPassword("122234234512223423451222342345122234234545555");
//        userDao.persist(user2);
//
//
//        GenericDao<Order,Integer>  orderDao = JdbcDaoFactory.getInstance().getDao(Order.class);
//        Order orderOne = new Order();
//        orderOne.setFinishTime(new Timestamp(66666653255555L));
//        orderOne.setExpectedTime(new Timestamp(66666653259999L));
//        orderOne.setStartTime(new Timestamp(6666665323000L));
//        orderOne.setDescription(" aaa bbb ccc");
//        orderOne.setFinishPoint("point B");
//        orderOne.setStartPoint("Point A");
//        orderOne.setIdStatus(1);
//        orderOne.setPrice(new BigDecimal(12.4000).round(new MathContext(4)));
//        orderOne.setIdCourier(0);
//        orderOne.setIdCustomer(0);
//
//        Order orderTwo = new Order();
//        orderTwo.setFinishTime(new Timestamp(66666653255555L));
//        orderTwo.setExpectedTime(new Timestamp(66666653259999L));
//        orderTwo.setStartTime(new Timestamp(6666665323000L));
//        orderTwo.setDescription(" aaa bbb ccc");
//        orderTwo.setFinishPoint("point B");
//        orderTwo.setStartPoint("Point A");
//        orderTwo.setIdStatus(1);
//        orderTwo.setPrice(new BigDecimal(12.4000).round(new MathContext(4)));
//        orderTwo.setIdCourier(1);
//        orderTwo.setIdCustomer(1);
//        System.out.println(orderOne.getId());
//        System.out.println(orderTwo.getId());
//
//        orderDao.persist(orderOne);
//        orderDao.persist(orderTwo);
//        System.out.println(orderOne.getId());
//        System.out.println(orderTwo.getId());
//
//        List<Order> orders = orderDao.getAll();
//        Assert.assertEquals(2,orders.size());
//        Assert.assertEquals(orderOne , orders.get(0));
//        Assert.assertEquals(orderTwo , orders.get(1));
    }



}