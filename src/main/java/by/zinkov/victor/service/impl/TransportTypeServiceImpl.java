package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.TransportTypeExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.UtilValidator;

import java.util.List;

public class TransportTypeServiceImpl implements TransportTypeService {

    @Override
    public TransportType getById(Integer pk) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = (GenericDao<TransportType, Integer>) daoFactory.getDao(TransportType.class);
            return dao.getByPK(pk);
        } catch (DaoException e) {
            throw new ServiceException("Problem with get all TransportType by pk", e);
        }
    }

    @Override
    public TransportType getByCourierId(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            TransportTypeExpandedDao dao = (TransportTypeExpandedDao) daoFactory.getDao(TransportType.class);
            return dao.getByCourierId(courierId);
        } catch (DaoException e) {
            throw new ServiceException("Problem with get all TransportTypes", e);
        }
    }

    @Override
    public List<TransportType> getAllTransportTypes() throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("Problem with get all TransportTypes", e);
        }
    }

    @Override
    public void delete(Integer transportTypeId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            TransportType transportType = dao.getByPK(transportTypeId);
            dao.delete(transportType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with delete by id: " + transportTypeId, e);
        }
    }

    @Override
    public void add(String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            TransportType transportType = new TransportType();
            transportType.setTransportType(name);
            dao.persist(transportType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with add transport with bane: " + name, e);
        }
    }


    @Override
    public void edit(String id, String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            UtilValidator stringValidator = UtilValidator.getInstance();
            stringValidator.isMatchesInt(id, UtilValidator.POSITIVE_RANGE);
            Integer transportId = Integer.valueOf(id);

            TransportType transportType = new TransportType();
            transportType.setTransportType(name);
            transportType.setId(transportId);

            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            dao.update(transportType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with edit transport with bane: " + name, e);
        }
    }

    @Override
    public TransportType getByName(String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            TransportTypeExpandedDao dao = (TransportTypeExpandedDao) daoFactory.getDao(TransportType.class);
            return dao.getByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Cannot find by name!", e);
        }
    }
}
