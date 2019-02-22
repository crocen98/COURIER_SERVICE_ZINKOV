package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.DaoFactoryType;
import by.zinkov.victor.dao.FactoryProducer;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.exception.PersistException;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.StringValidator;
import by.zinkov.victor.service.validation.impl.UserValidator;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {
    @Override
    public User signUp(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            UserValidator validator = new UserValidator();
            validator.validate(user);

            GenericDao<UserStatus, Integer> userStatusDao = daoFactory.getDao(UserStatus.class);
            UserStatus userStatus = userStatusDao.persist(UserStatus.WAITING_CONFIRMATION);

            user.setUserStatus(userStatus.getId());


            userDao.persist(user);

        } catch (DaoException e) {
            throw new ServiceException("Failed  with DAO. ", e);

        } catch (ValidationException e) {
            throw new ServiceException(e);
        }

        //provide your code here
        throw new UnsupportedOperationException();
    }
}
