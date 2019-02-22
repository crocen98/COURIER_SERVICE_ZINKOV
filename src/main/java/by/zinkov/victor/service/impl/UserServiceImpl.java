package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.impl.UserValidator;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {
    @Override
    public User signUp(User user, String userRoleString) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);

            UserRole role = UserRole.valueOf(userRoleString);
            UserValidator validator = new UserValidator();
            validator.validate(user);

            GenericDao<UserStatus, Integer> userStatusDao = daoFactory.getDao(UserStatus.class);
            Integer userStatusId = UserStatus.WAITING_CONFIRMATION.getId();

            user.setUserStatus(userStatusId);

            UserRoleExpanded userRoleDao = (UserRoleExpanded) daoFactory.getDao(UserRole.class);
            UserRole userRole = userRoleDao.getByName(userRoleString);

            user.setUserRoleId(userRole.getId());

            userDao.persist(user);
            return user;
        } catch (DaoException e) {
            throw new ServiceException("Failed  with DAO. ", e);

        } catch (ValidationException e) {
            throw new ServiceException(e);
        }
    }
}
