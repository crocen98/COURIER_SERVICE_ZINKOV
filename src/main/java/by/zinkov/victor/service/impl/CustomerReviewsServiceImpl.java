package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.CustomerReviewsExpandedDao;
import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
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
        try {
            CustomerReviewsExpandedDao dao = (CustomerReviewsExpandedDao) daoFactory.getDao(CustomerReviews.class);
            CustomerReviews customerReviews = dao.getByCourierUserId(courierId, userId);
            customerReviews.setMark((byte) (int) rating);
            ((GenericDao<CustomerReviews, Integer>) dao).update(customerReviews);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot update mark for courier by id!", e);
            exception.setErrorKey("update_courier_mark");
            throw exception;
        }
    }

    @Override
    public void setCourierMark(Integer courierId, Integer userId, Integer rating) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CustomerReviews, Integer> dao = daoFactory.getDao(CustomerReviews.class);
            CustomerReviews customerReviews = new CustomerReviews();
            customerReviews.setCustomerId(userId);
            customerReviews.setCourierId(courierId);
            int intRating = rating;
            customerReviews.setMark((byte) intRating);
            dao.persist(customerReviews);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot calculate mark for courier by id!", e);
            exception.setErrorKey("calculate_mark_by_courier_id");
            throw exception;
        }
    }

    @Override
    public Double getCourierMark(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            CustomerReviewsExpandedDao dao = (CustomerReviewsExpandedDao) daoFactory.getDao(CustomerReviews.class);
            return dao.getCourierMark(courierId);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get mark for courier by courier id!", e);
            exception.setErrorKey("find_courier_mark_by_id");
            throw exception;
        }
    }
}
