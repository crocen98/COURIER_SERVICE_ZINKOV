package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.CustomerReviewsExpandedDao;
import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CustomerReviews;
import by.zinkov.victor.service.CustomerReviewsService;
import by.zinkov.victor.service.ServiceException;

public class CustomerReviewsServiceImpl implements CustomerReviewsService {
    @Override
    public Double getCourierMark(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            CustomerReviewsExpandedDao dao =   (CustomerReviewsExpandedDao) daoFactory.getDao(CustomerReviews.class);
            return dao.getCourierMark(courierId);
        } catch (DaoException e) {
            throw new ServiceException("Cannot calculate mark for courier by id!", e);
        }
    }
}
