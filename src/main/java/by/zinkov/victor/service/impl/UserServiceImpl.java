package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.RegistrationKey;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.UserRoleService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.StringValidator;
import by.zinkov.victor.service.validation.UserValidator;
import by.zinkov.victor.util.MailSender;
import by.zinkov.victor.util.StringGenerator;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {


    @Override
    public void changePassword(String id, String password, String activateString) throws ServiceException {
        StringValidator validator = StringValidator.getInstance();

        try {
            validator.isMatchesInt(id , StringValidator.POSITIVE_RANGE);
            validator.simpleStingMatches(password , 45 , "password");
            validator.simpleStingMatches(activateString,32 ,activateString );

            RegistrationKeyService service = new RegistrationKeyServiceImpl();
            RegistrationKey key = service.getById(Integer.valueOf(id));

            DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
            GenericDao<User,Integer> userDao = daoFactory.getDao(User.class);
            User user = userDao.getByPK(Integer.valueOf(id));
            user.setPassword(password);
            userDao.update(user);
            service.remove(key);
            if(!Objects.equals(key.getKey() , activateString)){
                throw new ServiceException("Secure problems! Key does not belong your!");
            }
        } catch (ValidationException | DaoException e) {
            throw new ServiceException("Cannot change password", e);
        }

    }

    private String registrationLinkBuild(String randomString, Integer userId, String url) {
        return String.format("Hi!  your activation link: %s/couriers?command=activate&user_id=%d&value=%s", url, userId, randomString);
    }

    private String restoreLinkBuild(String randomString, Integer userId, String url) {
        return String.format("Hi!  your restore link: %s/couriers?command=to_change_password_page&user_id=%d&value=%s", url, userId, randomString);
    }

    private void sendActivateEmail(User user, HttpServletRequest request) throws ServiceException {
        StringGenerator generator = new StringGenerator();
        String randomString = generator.generate();
        MailSender sender = MailSender.getInstance();
        Integer port = request.getLocalPort();
        String url;
        try {
            url = InetAddress.getLocalHost() + ":" + port + request.getContextPath();
        } catch (UnknownHostException e) {
            throw new ServiceException(e);
        }
        String activateLink = restoreLinkBuild(randomString, user.getId(), url);
        sender.sendEmail(activateLink, user.getEmail());
        RegistrationKeyService registrationKeyService = new RegistrationKeyServiceImpl();
        registrationKeyService.add(user.getId(), randomString);
    }


    @Override
   public void restoreUserByEmail(User user , HttpServletRequest request) throws ServiceException {
        JdbcDaoFactory daoFactory = (JdbcDaoFactory) FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao  userDao = (UserExpandedDao)daoFactory.getDao(User.class);
            StringValidator validator = StringValidator.getInstance();
            validator.emailMatches(user.getEmail());
            validator.simpleStingMatches(user.getLogin(),45 , "login");
            validator.phoneMatches(user.getPhone());

            User userFromDb = userDao.getByLogin(user.getLogin());
            if(userFromDb == null
                    || !Objects.equals(userFromDb.getEmail(), user.getEmail())
                    || !Objects.equals(userFromDb.getPhone(), user.getPhone())){
                throw new ServiceException("Cannot find user by email.");
            }
            sendActivateEmail(userFromDb ,request);
        } catch (DaoException e) {
            throw new ServiceException("Failed  with DAO. ", e);
        } catch (ValidationException e) {
            throw new ServiceException("Failed  with Validation. ", e);

        }
    }

    @Override
    public User signUp(User user, String userRole) throws ServiceException {
        JdbcDaoFactory daoFactory = (JdbcDaoFactory) FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserValidator validator = new UserValidator();
            validator.validate(user, userRole);

            UserRoleService userRoleService = new UserRoleServiceImpl();
            UserRole role = userRoleService.getByName(userRole);
            user.setUserRoleId(role.getId());
            if(role == UserRole.ADMINISTRATOR ){
                throw  new ServiceException("Attempt to get unsupported rights");
            }

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
    public UserDto LogIn(String login, String password) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userDao.logIn(login, password);
        } catch (DaoException e) {
            throw new ServiceException("Incorrect login or password", e);
        }
    }


    @Override
    public UserDto getByPK(Integer id) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userDao.getDtoByPK(id);
        } catch (DaoException e) {
            throw new ServiceException("Cannot find user by id", e);
        }
    }





    @Override
    public void setNewStatus(Integer id, String status) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            GenericDao<User, Integer> genericUserDao = (GenericDao<User, Integer>) userDao;
            User user = genericUserDao.getByPK(id);
            user.setUserStatus(UserStatus.ACTIVE.getId());
            genericUserDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Cannot find user by id", e);
        }
    }
}
