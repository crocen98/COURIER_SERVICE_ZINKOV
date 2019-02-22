package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CustomerReviews;
import by.zinkov.victor.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class CustomerReviewsDaoTest  extends AbstractDaoTest {
    @Test
    public void insertNewCustomerReviews() throws DaoException {


        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User user = new User();
        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatus(1);
        user.setUserRole(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<CustomerReviews,Integer> customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviews = new CustomerReviews();
        customerReviews.setCourierId(0);
        customerReviews.setCustomerId(0);
        customerReviews.setMark((byte) 1);
        Assert.assertEquals(null,customerReviews.getId());
        customerReviewsDao.persist(customerReviews);

    }


    @Test(expected = DaoException.class)
    public void insertExistingCustomerReviewsAndThrowException() throws DaoException {
        GenericDao<CustomerReviews,Integer>  customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviews = new CustomerReviews();
        customerReviews.setMark((byte) 1);
        Assert.assertEquals(null,customerReviews.getId());
        customerReviewsDao.persist(customerReviews);
        Assert.assertEquals((Integer) 0,customerReviews.getId());
        customerReviewsDao.persist(customerReviews);
    }



    @Test
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User user = new User();
        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatus(1);
        user.setUserRole(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<CustomerReviews,Integer> customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviewsOne = new CustomerReviews();
        customerReviewsOne.setCourierId(0);
        customerReviewsOne.setCustomerId(0);
        customerReviewsOne.setMark((byte) 1);

        customerReviewsDao.persist(customerReviewsOne);


        List<CustomerReviews> customerReviews = customerReviewsDao.getAll();
        Assert.assertEquals(1,customerReviews.size());
        customerReviewsDao.delete(customerReviewsOne);
        customerReviews = customerReviewsDao.getAll();
        Assert.assertEquals(0,customerReviews.size());
    }

    @Test
    public void insertOneObjectAndFindByPKOne() throws DaoException {
        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User user = new User();
        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatus(1);
        user.setUserRole(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<CustomerReviews,Integer> customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviewsOne = new CustomerReviews();
        customerReviewsOne.setCourierId(0);
        customerReviewsOne.setCustomerId(0);
        customerReviewsOne.setMark((byte) 1);

        customerReviewsDao.persist(customerReviewsOne);

        CustomerReviews customerReviewsTest = customerReviewsDao.getByPK(0);
        Assert.assertEquals(customerReviewsOne.getId(),customerReviewsTest.getId());
        Assert.assertEquals(customerReviewsOne,customerReviewsTest);
    }

}