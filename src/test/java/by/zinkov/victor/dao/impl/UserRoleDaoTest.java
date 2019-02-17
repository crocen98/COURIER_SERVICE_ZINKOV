package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.UserRole;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class UserRoleDaoTest extends AbstractDaoTest {
    @Test(expected = DaoException.class)
    public void insertNewUserRole() throws DaoException {
        GenericDao<UserRole, Integer> userRoleDao = JdbcDaoFactory.getInstance().getDao(UserRole.class);
        UserRole userRole = UserRole.ADMINISTRATOR;
        userRole.setId(userRole.getId() - 1);
        userRoleDao.persist(userRole);
    }


    @Test
    public void readTwoObject() throws DaoException {
        GenericDao<UserRole, Integer> userRoleDao = JdbcDaoFactory.getInstance().getDao(UserRole.class);
        List<UserRole> userRoles = userRoleDao.getAll();
        Assert.assertEquals(3, userRoles.size());

    }

    @Test(expected = DaoException.class)
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<UserRole, Integer> userRoleDao = JdbcDaoFactory.getInstance().getDao(UserRole.class);
        UserRole status = UserRole.ADMINISTRATOR;
        status.setId(status.getId() - 1);
        userRoleDao.delete(status);
    }
}