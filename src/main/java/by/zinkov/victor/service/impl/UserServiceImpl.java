package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.UserStatusDao;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserRoleService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User signUp(String firstName, String lastName, String password , String login, String email , String phone , String location, String userRole) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setLogin(login);
            user.setEmail(email);
            user.setPhone(phone);
            user.setLocation(location);

            UserValidator validator = new UserValidator();
            validator.validate(user, userRole );

            UserRoleService userRoleService = new UserRoleServiceImpl();
            UserRole role =userRoleService.getByName(userRole);
            user.setUserRoleId(role.getId());

            user.setUserStatus(UserStatus.WAITING_CONFIRMATION.getId());


            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);

            return userDao.persist(user);
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


    @Override
    public UserDto getByPK(Integer id) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userDao.getDtoByBK(id);
        } catch (DaoException e) {
            throw new ServiceException("Cannot find user by id",e);
        }
    }


    @Override
    public void setNewStatus(Integer id,String status) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            GenericDao<User,Integer> genericUserDao = (GenericDao<User, Integer>) userDao;
            User user = genericUserDao.getByPK(id);
            user.setUserStatus(UserStatus.ACTIVE.getId());
            genericUserDao.update(user);

//            GenericDao<UserStatus,Integer> genericUserStatusDao = daoFactory.getDao(UserStatus.class);
//            List<UserStatus> userStatuses = genericUserStatusDao.getAll();
//
//            for(UserStatus userStatus : userStatuses){
//                if(userStatus.toString().equalsIgnoreCase(UserStatus.ACTIVE.toString())){
//                    user.setUserStatus(userStatus.getId());
//                    genericUserDao.update(user);
//                    break;
//                }
//            }

        } catch (DaoException e) {
            throw new ServiceException("Cannot find user by id",e);
        }
    }
}
