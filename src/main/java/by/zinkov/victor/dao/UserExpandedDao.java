package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dto.UserDto;

public interface UserExpandedDao {
    @AutoConnection
    UserDto logIn(String login , String password) throws DaoException;
}
