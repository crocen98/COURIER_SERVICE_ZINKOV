package by.zinkov.victor.service;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Example of user service
 */
public interface UserService {

    /**
     * Sign up user
     * @return - saved user
     * @throws ServiceException should be clarify
     */
    User signUp(User user, String userRole) throws ServiceException;

    UserDto LogIn(String login , String password) throws ServiceException ;

    UserDto getByPK(Integer id) throws ServiceException;
    void setNewStatus(Integer id , String status) throws ServiceException;
    void restoreUserByEmail(User user , HttpServletRequest request) throws ServiceException;

    void changePassword(String id , String password , String activateString) throws ServiceException;




}
