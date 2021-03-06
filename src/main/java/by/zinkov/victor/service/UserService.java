package by.zinkov.victor.service;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;

import java.util.List;
import java.util.Map;

/**
 * Example of user service
 */
public interface UserService {

    /**
     * Sign up user
     *
     * @return - saved user
     * @throws ServiceException should be clarify
     */

    int getUsersCount() throws ServiceException;

    User signUp(User user, String url) throws ServiceException;

    UserDto LogIn(String login, String password) throws ServiceException;

    UserDto getByPK(Integer id) throws ServiceException;

    User getByLogin(String login) throws ServiceException;

    void setNewStatus(Integer id, String status) throws ServiceException;

    void restoreUserByEmail(User user, String url) throws ServiceException;

    void changePassword(String id, String password, String activateString) throws ServiceException;

    Map<User, Double> getCouriersByParams(String transportType, String cargoType) throws ServiceException;

    void update(User user) throws ServiceException;

    List<User> getClientCouriers(Integer clientId) throws ServiceException;


    List<User> getAll() throws ServiceException;

    List<UserDto> getAllUsersDto(int start) throws ServiceException;


    void changeStatus(Integer userId) throws ServiceException;
}
