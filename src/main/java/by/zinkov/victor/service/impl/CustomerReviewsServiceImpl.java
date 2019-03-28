package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.CustomerReviewsExpandedDao;
import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.dao.impl.TransactionManager;
import by.zinkov.victor.domain.CustomerReviews;
import by.zinkov.victor.service.CustomerReviewsService;
import by.zinkov.victor.service.ServiceException;

public class CustomerReviewsServiceImpl implements CustomerReviewsService {

    @Override
    public boolean haveMark(Integer courierId, Integer userId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            CustomerReviewsExpandedDao dao = (CustomerReviewsExpandedDao) daoFactory.getDao(CustomerReviews.class);
            return dao.haveMark(courierId, userId);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot calculate mark for courier by id!", e);
            exception.setErrorKey("calculate_mark_by_courier_id");
            throw exception;
        }
    }

    @Override
    public void updateCourierMark(Integer courierId, Integer userId, Integer rating) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        TransactionManager manager = new TransactionManager();
        try {
            CustomerReviewsExpandedDao dao = (CustomerReviewsExpandedDao) ((JdbcDaoFactory) daoFactory).getTransactionalDao(CustomerReviews.class);
            manager.begin((AbstractJdbcDao) dao);
            CustomerReviews customerReviews = dao.getByCourierUserId(courierId, userId);
            customerReviews.setMark((byte) (int) rating);
            dao.update(customerReviews);
            manager.commit();
        } catch (DaoException e) {
            try {
                manager.rollback();
            } catch (DaoException e1) {
                throw new RuntimeException("problem in transaction");
            }
            ServiceException exception = new ServiceException("Cannot update mark for courier by id!", e);
            exception.setErrorKey("update_courier_mark");
            throw exception;
        } finally {
            try {
                manager.end();
            } catch (DaoException e) {
                throw new RuntimeException("problem in transaction");
            }
        }
    }

    @Override
    public void setCourierMark(Integer courierId, Integer userId, Integer rating) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        TransactionManager manager = new TransactionManager();
        try {
            CustomerReviewsExpandedDao dao = (CustomerReviewsExpandedDao) ((JdbcDaoFactory) daoFactory).getTransactionalDao(CustomerReviews.class);
            manager.begin((AbstractJdbcDao) dao);
            CustomerReviews customerReviews = dao.getByCourierUserId(courierId, userId);
            if (customerReviews != null){
                customerReviews.setMark((byte) (int) rating);
                dao.update(customerReviews);
            } else {
                customerReviews = new CustomerReviews();
                customerReviews.setCourierId(courierId);
                customerReviews.setCustomerId(userId);
                customerReviews.setMark((byte) (int) rating);
                dao.persist(customerReviews);
            }
            manager.commit();
        } catch (DaoException e) {
            try {
                manager.rollback();
            } catch (DaoException e1) {
                throw new RuntimeException("problem in transaction");
            }
            ServiceException exception = new ServiceException("Cannot update mark for courier by id!", e);
            exception.setErrorKey("update_courier_mark");
            throw exception;
        } finally {
            try {
                manager.end();
            } catch (DaoException e) {
                throw new RuntimeException("problem in transaction");
            }
        }






    }

    @Override
    public Double getCourierMark(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        TransactionManager manager = new TransactionManager();
        try {
            CustomerReviewsExpandedDao dao = (CustomerReviewsExpandedDao) ((JdbcDaoFactory) daoFactory).getTransactionalDao(CustomerReviews.class);
            manager.begin((AbstractJdbcDao) dao);
            manager.commit();
            return dao.getCourierMark(courierId);

        } catch (DaoException e) {
            try {
                manager.rollback();
            } catch (DaoException e1) {
                throw new RuntimeException(e);
            }
            ServiceException exception = new ServiceException("Cannot get mark for courier by courier id!", e);
            exception.setErrorKey("find_courier_mark_by_id");
            throw exception;
        } finally {
            try {
                manager.end();
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
