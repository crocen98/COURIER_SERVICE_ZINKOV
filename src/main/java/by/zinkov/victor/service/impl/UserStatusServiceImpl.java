package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.UserStatusExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserStatusService;

public class UserStatusServiceImpl implements UserStatusService {
    @Override
    public UserStatus getByName(String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            UserStatusExpandedDao dao = (UserStatusExpandedDao) daoFactory.getDao(UserStatus.class);
            return dao.getByName(name);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get  UserStatus by name!", e);
            exception.setErrorKey("get_user_status_by_name");
            throw exception;
        }
    }
}
