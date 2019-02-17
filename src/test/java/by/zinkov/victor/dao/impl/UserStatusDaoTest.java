package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.UserStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class UserStatusDaoTest extends AbstractDaoTest {
    @Test(expected = UnsupportedOperationException.class)
    public void insertNewUserStatus() throws DaoException {
        GenericDao<UserStatus, Integer> userStatus = JdbcDaoFactory.getInstance().getDao(UserStatus.class);
        UserStatus status = UserStatus.BLOCKED;
        status.setId(status.getId() - 1);
        userStatus.persist(status);
    }


    @Test
    public void readTwoObject() throws DaoException {
        GenericDao<UserStatus, Integer> userStatus = JdbcDaoFactory.getInstance().getDao(UserStatus.class);
        List<UserStatus> cargoTypes = userStatus.getAll();
        Assert.assertEquals(3, cargoTypes.size());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<UserStatus, Integer> userStatus = JdbcDaoFactory.getInstance().getDao(UserStatus.class);
        UserStatus status = UserStatus.BLOCKED;
        status.setId(status.getId() - 1);
        userStatus.delete(status);
    }
}