package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;

import java.util.List;
import java.util.Map;

public interface UserExpandedDao {
    @AutoConnection
    UserDto logIn(String login, String password) throws DaoException;

    @AutoConnection
    UserDto getDtoByPK(Integer id) throws DaoException;


    @AutoConnection
    User getByLogin(String login) throws DaoException;

    @AutoConnection
    Map<User, Double> getCouriersWithAppropriateCargoAndTransportType(String transportType, String cargoType) throws DaoException;

    @AutoConnection
    List<User> getClientCouriers(Integer clientId) throws DaoException;

    @AutoConnection
    List<UserDto> getAllUsersDto(int start) throws DaoException;

    @AutoConnection
    int countUsers() throws DaoException;
}
