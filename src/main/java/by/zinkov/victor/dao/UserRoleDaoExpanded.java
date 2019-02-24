package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.UserRole;

public interface UserRoleDaoExpanded {
    @AutoConnection
    UserRole getByName(String name) throws DaoException;
}
