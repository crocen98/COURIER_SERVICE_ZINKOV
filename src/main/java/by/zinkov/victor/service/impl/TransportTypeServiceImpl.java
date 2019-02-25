package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.StringValidator;
import by.zinkov.victor.service.validation.TransportTypeValidator;

import java.util.List;

public class TransportTypeServiceImpl implements TransportTypeService {
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
            TransportTypeValidator transportTypeValidator = new TransportTypeValidator();
            transportTypeValidator.validate(transportType);
            dao.persist(transportType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with add transport with bane: " + name, e);
        } catch (ValidationException e) {
            throw new ServiceException("problem with validation", e);
        }
    }


    @Override
    public void edit(String id, String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            StringValidator stringValidator = StringValidator.getInstance();
            stringValidator.isMatchesInt(id, StringValidator.POSITIVE_RANGE);
            Integer transportId = Integer.valueOf(id);

            TransportType transportType = new TransportType();
            transportType.setTransportType(name);
            transportType.setId(transportId);

            TransportTypeValidator transportTypeValidator = new TransportTypeValidator();
            transportTypeValidator.validate(transportType);

            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            dao.update(transportType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with edit transport with bane: " + name, e);
        } catch (ValidationException e) {
            throw new ServiceException("problem with validation", e);
        }
    }
}
