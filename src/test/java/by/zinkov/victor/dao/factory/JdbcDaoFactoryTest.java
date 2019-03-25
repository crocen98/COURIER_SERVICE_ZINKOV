package by.zinkov.victor.dao.factory;

import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.UserExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.User;
import org.junit.Assert;
import org.junit.Test;

public class JdbcDaoFactoryTest {

    @Test
    public void shouldGetDaoClass() throws DaoException {
        JdbcDaoFactory factory = JdbcDaoFactory.getInstance();
        factory.getDao(User.class);
    }

    @Test(expected = DaoException.class)
    public void getNotSupportedClassAndThrowException() throws DaoException {
        JdbcDaoFactory factory = JdbcDaoFactory.getInstance();
        factory.getDao(Object.class);
    }

    @Test(expected = RuntimeException.class)
    public void getTransactionalDaoAndThrowRuntimeException() throws DaoException {
        JdbcDaoFactory factory = JdbcDaoFactory.getInstance();
        GenericDao<User,Integer> dao = factory.getTransactionalDao(User.class);
        dao.persist(new User());
    }

}