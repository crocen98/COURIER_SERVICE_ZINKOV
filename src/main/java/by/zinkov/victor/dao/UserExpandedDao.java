package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;

public interface UserExpandedDao {
    @AutoConnection
    UserDto logIn(String login , String password) throws DaoException;

    @AutoConnection
    UserDto getDtoByPK(Integer id) throws DaoException;


    @AutoConnection
    User getByLogin(String login) throws  DaoException;
}
