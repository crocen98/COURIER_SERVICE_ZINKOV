package by.zinkov.victor.service;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.exception.ServiceException;

/**
 * Example of user service
 */
public interface UserService {

    /**
     * Sign up user
     * @param user - User
     * @return - saved user
     * @throws ServiceException should be clarify
     */
    User signUp(User user) throws ServiceException;

    // Provide your code here

}