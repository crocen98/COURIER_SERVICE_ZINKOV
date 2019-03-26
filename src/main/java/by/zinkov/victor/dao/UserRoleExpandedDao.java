package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.UserRole;

public interface UserRoleExpandedDao extends GenericDao<UserRole, Integer> {
    @AutoConnection
    UserRole getByName(String name) throws DaoException;


}
