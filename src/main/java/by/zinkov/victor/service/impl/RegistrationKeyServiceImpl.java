package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.RegistrationKey;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.exception.ServiceException;

public class RegistrationKeyServiceImpl implements RegistrationKeyService {
    @Override
    public void add(Integer id, String randomString) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();

        RegistrationKey key = new RegistrationKey();
        key.setId(id);
        key.setKey(randomString);
        try {
            GenericDao<RegistrationKey, Integer> registrationKeyDao = daoFactory.getDao(RegistrationKey.class);
            registrationKeyDao.persist(key);
        } catch (DaoException e) {
            throw new ServiceException("Problem with save activate key", e);
        }
    }


    @Override
    public RegistrationKey getById(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<RegistrationKey, Integer> registrationKeyDao = daoFactory.getDao(RegistrationKey.class);
            return registrationKeyDao.getByPK(id);
        } catch (DaoException e) {
            throw new ServiceException("Problem with get activate key", e);
        }
    }


    @Override
    public void remove(RegistrationKey key) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<RegistrationKey, Integer> registrationKeyDao = daoFactory.getDao(RegistrationKey.class);
            registrationKeyDao.delete(key);
        } catch (DaoException e) {
            throw new ServiceException("Problem with get delete key", e);
        }
    }
}
