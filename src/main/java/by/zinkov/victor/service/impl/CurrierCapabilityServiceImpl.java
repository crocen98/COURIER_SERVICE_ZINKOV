package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.CurrierCapabilityExpandedDao;
import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CurrierCapability;
import by.zinkov.victor.service.CurrierCapabilityService;
import by.zinkov.victor.service.ServiceException;

public class CurrierCapabilityServiceImpl implements CurrierCapabilityService {

    @Override
    public void update(CurrierCapability currierCapability) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CurrierCapability,Integer> dao = (GenericDao<CurrierCapability,Integer>) daoFactory.getDao(CurrierCapability.class);
             dao.update(currierCapability);
        } catch (DaoException e) {
            throw new ServiceException("Cannot get currier capability by courier id", e);
        }
    }

    @Override
    public CurrierCapability getByCourierId(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            CurrierCapabilityExpandedDao dao = (CurrierCapabilityExpandedDao) daoFactory.getDao(CurrierCapability.class);
            return dao.getByCourierId(courierId);
        } catch (DaoException e) {
            throw new ServiceException("Cannot get currier capability by courier id", e);
        }
    }

    @Override
    public CurrierCapability create(CurrierCapability currierCapability) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CurrierCapability, Integer> dao = (GenericDao<CurrierCapability, Integer>) daoFactory.getDao(CurrierCapability.class);
            return dao.persist(currierCapability);
        } catch (DaoException e) {
            throw new ServiceException("Cannot create currier capability!", e);
        }
    }
}
