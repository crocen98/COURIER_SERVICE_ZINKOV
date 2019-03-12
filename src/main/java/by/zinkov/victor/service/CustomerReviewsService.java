package by.zinkov.victor.service;

public interface CustomerReviewsService {
    Double getCourierMark(Integer courierId) throws ServiceException;

    boolean haveMark(Integer courierId, Integer userId) throws ServiceException;

    void updateCourierMark(Integer courierId, Integer userId, Integer rating) throws ServiceException;

    void setCourierMark(Integer courierId, Integer userId, Integer rating) throws ServiceException;

}
