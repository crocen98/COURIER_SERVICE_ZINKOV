package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User signUp(User user, String userRoleString) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {


            // not gooooood
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            UserRole role = UserRole.valueOf(userRoleString.toUpperCase());

            user.setUserRoleId(role.getId());

            UserValidator validator = new UserValidator();
            validator.validate(user);
            GenericDao<UserStatus, Integer> userStatusDao = daoFactory.getDao(UserStatus.class);
            Integer userStatusId = UserStatus.WAITING_CONFIRMATION.getId();

            user.setUserStatus(userStatusId);

            UserRoleExpandedDao userRoleDao = (UserRoleExpandedDao) daoFactory.getDao(UserRole.class);
            //UserRole userRole = userRoleDao.getByName(userRoleString);

           // user.setUserRoleId(userRole.getId());
            user.setUserRoleId(UserRole.ADMINISTRATOR.getId());
            LOGGER.info(user + " in service");
            userDao.persist(user);
            return user;
        } catch (DaoException e) {
            throw new ServiceException("Failed  with DAO. ", e);

        } catch (ValidationException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public UserDto LogIn(String login, String password) throws ServiceException  {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userDao.logIn(login,password);
        } catch (DaoException e) {
            throw new ServiceException("Incorrect login or password",e);
        }
    }
}
