package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.CustomerReviews;

public interface CustomerReviewsExpandedDao {
    @AutoConnection
    Double getCourierMark(Integer courierId) throws DaoException;

    @AutoConnection
    CustomerReviews getByCourierUserId(Integer courierId, Integer userId) throws DaoException;

    @AutoConnection
    boolean haveMark(Integer courierId, Integer userId) throws DaoException;
}
