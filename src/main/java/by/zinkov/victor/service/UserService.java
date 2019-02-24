package by.zinkov.victor.service;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.exception.ServiceException;

/**
 * Example of user service
 */
public interface UserService {

    /**
     * Sign up user
     * @return - saved user
     * @throws ServiceException should be clarify
     */
    User signUp(String firstName, String LastName, String password , String loggin, String email , String phone , String location, String userRole) throws ServiceException;

    UserDto LogIn(String login , String password) throws ServiceException ;

    UserDto getByPK(Integer id) throws ServiceException;
    void setNewStatus(Integer id , String status) throws ServiceException;




}
