package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.UserRoleDaoExpanded;
import by.zinkov.victor.dao.UserRoleExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.service.UserRoleService;
import by.zinkov.victor.service.exception.ServiceException;

import java.util.List;

public class UserRoleServiceImpl implements UserRoleService {
    @Override
    public List<UserRole> getAll() throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<UserRole, Integer> dao = daoFactory.getDao(UserRole.class);
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("Problem with get all UserRole", e);
        }
    }

    @Override
    public UserRole getByName(String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            UserRoleExpandedDao dao = (UserRoleExpandedDao) daoFactory.getDao(UserRole.class);
            return dao.getByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Problem with get all UserRole", e);
        }
    }
}
