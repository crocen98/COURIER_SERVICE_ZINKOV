package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.DaoFactoryType;
import by.zinkov.victor.dao.FactoryProducer;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.exception.PersistException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {
    @Override
    public User signUp(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);

        //provide your code here

        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            userDao.persist(user);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        }

        //provide your code here

        throw new UnsupportedOperationException();
    }
}
