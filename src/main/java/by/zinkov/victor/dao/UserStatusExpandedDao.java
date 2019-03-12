package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.UserStatus;

public interface UserStatusExpandedDao {
    @AutoConnection
    UserStatus getByName(String name) throws DaoException;
}
