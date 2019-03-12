package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;

public interface CustomerReviewsExpandedDao {
    @AutoConnection
    Double getCourierMark(Integer courierId) throws DaoException;
}
