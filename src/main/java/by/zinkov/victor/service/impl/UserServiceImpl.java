package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.*;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.util.MailSender;
import by.zinkov.victor.util.StringGenerator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {


    @Override
    public void changeStatus(Integer userId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<User, Integer> dao = (GenericDao<User, Integer>) daoFactory.getDao(User.class);
            GenericDao<UserStatus, Integer> userStatusDao = (GenericDao<UserStatus, Integer>) daoFactory.getDao(UserStatus.class);

            User user = dao.getByPK(userId);
            UserStatus userStatus = userStatusDao.getByPK(user.getUserStatusId());
            UserStatus neededStatus;
            if (userStatus == UserStatus.BLOCKED) {
                neededStatus = ((UserStatusExpandedDao) userStatusDao).getByName(UserStatus.ACTIVE.toString());
            } else {
                neededStatus = ((UserStatusExpandedDao) userStatusDao).getByName(UserStatus.BLOCKED.toString());
            }
            user.setUserStatusId(neededStatus.getId());
            dao.update(user);

        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with change  UserStatus!", e);
            exception.setErrorKey("change_user_status");
            throw exception;
        }
    }

    @Override
    public List<User> getAll() throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<User, Integer> dao = (GenericDao<User, Integer>) daoFactory.getDao(User.class);
            return dao.getAll();
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get all users!", e);
            exception.setErrorKey("get_all_users");
            throw exception;
        }
    }

    @Override
    public List<User> getClientCouriers(Integer clientId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            UserExpandedDao dao = (UserExpandedDao) daoFactory.getDao(User.class);
            return dao.getClientCouriers(clientId);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get all Client's couriers!", e);
            exception.setErrorKey("get_clients_couriers");
            throw exception;
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<User, Integer> dao = (GenericDao<User, Integer>) daoFactory.getDao(User.class);
            dao.update(user);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with update information about user!", e);
            exception.setErrorKey("update_user");
            throw exception;
        }
    }

    @Override
    public User getByLogin(String login) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            UserExpandedDao dao = (UserExpandedDao) daoFactory.getDao(User.class);
            return dao.getByLogin(login);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get user by login!", e);
            exception.setErrorKey("get_user_by_login");
            throw exception;
        }
    }

    @Override
    public List<User> getCouriersByParams(String transportTypeString, String cargoTypeString) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            CargoTypeExpandedDao cargoTypeExpandedDao = (CargoTypeExpandedDao) daoFactory.getDao(CargoType.class);
            TransportTypeExpandedDao transportTypeExpandedDao = (TransportTypeExpandedDao) daoFactory.getDao(TransportType.class);
            CargoType cargoType = cargoTypeExpandedDao.getByName(cargoTypeString);
            TransportType transportType = transportTypeExpandedDao.getByName(transportTypeString);
            UserExpandedDao userExpandedDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userExpandedDao.getCouriersWithAppropriateCargoAndTransportType(transportType.getTransportType(), cargoType.getType());
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get  couriers by params!", e);
            exception.setErrorKey("get_couriers_by_params");
            throw exception;
        }
    }

    @Override
    public void changePassword(String id, String password, String activateString) throws ServiceException {
        UtilValidator validator = UtilValidator.getInstance();

        try {
            validator.isMatchesInt(id, UtilValidator.POSITIVE_RANGE);
            validator.simpleStingMatches(password, 45/*, "password"*/);
            validator.simpleStingMatches(activateString, 32/*, activateString*/);

            RegistrationKeyService service = new RegistrationKeyServiceImpl();
            RegistrationKey key = service.getById(Integer.valueOf(id));

            DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            User user = userDao.getByPK(Integer.valueOf(id));
            user.setPassword(password);
            userDao.update(user);
            service.remove(key);
            if (!Objects.equals(key.getKey(), activateString)) {
                ServiceException exception = new ServiceException("Secure problems! Key does not belong your!");
                exception.setErrorKey("secure_user");
                throw exception;
            }
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot change password", e);
            exception.setErrorKey("change_user_password");
            throw exception;
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
        url = "http://207.154.220.222" + ":" + port + request.getContextPath();
        String activateLink = restoreLinkBuild(randomString, user.getId(), url);
        sender.sendEmail(activateLink, user.getEmail());
        RegistrationKeyService registrationKeyService = new RegistrationKeyServiceImpl();
        registrationKeyService.add(user.getId(), randomString);
    }


    @Override
    public void restoreUserByEmail(User user, HttpServletRequest request) throws ServiceException {
        JdbcDaoFactory daoFactory = (JdbcDaoFactory) FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            UtilValidator validator = UtilValidator.getInstance();
            validator.emailMatches(user.getEmail());
            validator.simpleStingMatches(user.getLogin(), 45);
            validator.phoneMatches(user.getPhone());

            User userFromDb = userDao.getByLogin(user.getLogin());
            if (userFromDb == null
                    || !Objects.equals(userFromDb.getEmail(), user.getEmail())
                    || !Objects.equals(userFromDb.getPhone(), user.getPhone())) {
                ServiceException exception = new ServiceException("Cannot find user by email.");
                exception.setErrorKey("find_user_by_email");
                throw exception;
            }
            sendActivateEmail(userFromDb, request);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot restore user by email", e);
            exception.setErrorKey("restore_user_by_email");
            throw exception;
        }
    }

    @Override
    public User signUp(User user) throws ServiceException {
        JdbcDaoFactory daoFactory = (JdbcDaoFactory) FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            return userDao.persist(user);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot Sign up" , e);
            exception.setErrorKey("sign_up_user");
            throw exception;

        }
    }


    @Override
    public UserDto LogIn(String login, String password) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userDao.logIn(login, password);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Incorrect login or password" , e);
            exception.setErrorKey("user_log_in");
            throw exception;
        }
    }


    @Override
    public UserDto getByPK(Integer id) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userDao.getDtoByPK(id);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot find user by id" , e);
            exception.setErrorKey("find_user_by_id");
            throw exception;
        }
    }


    @Override
    public void setNewStatus(Integer id, String status) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            GenericDao<User, Integer> genericUserDao = (GenericDao<User, Integer>) userDao;
            User user = genericUserDao.getByPK(id);
            user.setUserStatusId(UserStatus.ACTIVE.getId());
            genericUserDao.update(user);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot set new user status!" , e);
            exception.setErrorKey("set_new_user_status");
            throw exception;
        }
    }


    @Override
    public List<UserDto> getAllUsersDto() throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserExpandedDao userDao = (UserExpandedDao) daoFactory.getDao(User.class);
            return userDao.getAllUsersDto();
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get all users!" , e);
            exception.setErrorKey("get_all_users");
            throw exception;
        }
    }
}
