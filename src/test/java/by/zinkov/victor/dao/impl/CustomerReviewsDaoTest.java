package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CustomerReviews;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class CustomerReviewsDaoTest  extends AbstractDaoTest {
    @Test
    public void insertNewCustomerReviews() throws DaoException {
        GenericDao<CustomerReviews,Integer> customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviews = new CustomerReviews();
        customerReviews.setMark((byte) 1);
        Assert.assertEquals(null,customerReviews.getId());
        customerReviewsDao.persist(customerReviews);
        customerReviews.setId(null);
        customerReviewsDao.persist(customerReviews);
        Assert.assertEquals((Integer)1,customerReviews.getId());
        customerReviews.setId(null);
        customerReviewsDao.persist(customerReviews);
        Assert.assertEquals((Integer)2,customerReviews.getId());
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
    public void insertTwoCustomerReviewsAndReadTwoObject() throws DaoException {
        GenericDao<CustomerReviews,Integer>  customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviewsOne = new CustomerReviews();
        customerReviewsOne.setMark((byte) 1);

        CustomerReviews customerReviewsTwo = new CustomerReviews();
        customerReviewsTwo.setMark((byte) 2);

        customerReviewsDao.persist(customerReviewsOne);
        customerReviewsDao.persist(customerReviewsTwo);

        List<CustomerReviews> customerReviews = customerReviewsDao.getAll();
        Assert.assertEquals(2,customerReviews.size());
        Assert.assertEquals(customerReviewsOne , customerReviews.get(0));
        Assert.assertEquals(customerReviewsTwo , customerReviews.get(1));
    }

    @Test
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<CustomerReviews,Integer>  customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviewsOne = new CustomerReviews();
        customerReviewsOne.setMark((byte) 1);

        CustomerReviews customerReviewsTwo = new CustomerReviews();
        customerReviewsTwo.setMark((byte) 2);

        customerReviewsDao.persist(customerReviewsOne);
        customerReviewsDao.persist(customerReviewsTwo);


        List<CustomerReviews> customerReviews = customerReviewsDao.getAll();
        Assert.assertEquals(2,customerReviews.size());
        customerReviewsDao.delete(customerReviewsOne);
        customerReviews = customerReviewsDao.getAll();
        Assert.assertEquals(1,customerReviews.size());
    }

    @Test
    public void insertTwoObjectAndFindByPKOne() throws DaoException {
        GenericDao<CustomerReviews,Integer>  customerReviewsDao = JdbcDaoFactory.getInstance().getDao(CustomerReviews.class);
        CustomerReviews customerReviewsOne = new CustomerReviews();
        customerReviewsOne.setMark((byte) 1);

        CustomerReviews customerReviewsTwo = new CustomerReviews();
        customerReviewsTwo.setMark((byte) 1);

        customerReviewsDao.persist(customerReviewsOne);
        customerReviewsDao.persist(customerReviewsTwo);

        CustomerReviews customerReviewsTest = customerReviewsDao.getByPK(0);
        Assert.assertEquals(customerReviewsOne.getId(),customerReviewsTest.getId());
        Assert.assertEquals(customerReviewsOne,customerReviewsTest);
    }

}