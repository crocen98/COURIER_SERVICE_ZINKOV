package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.TransportTypeExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.ServiceException;

import java.util.List;

public class TransportTypeServiceImpl implements TransportTypeService {

    @Override
    public TransportType getById(Integer pk) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = (GenericDao<TransportType, Integer>) daoFactory.getDao(TransportType.class);
            return dao.getByPK(pk);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get TransportType by pk", e);
            exception.setErrorKey("get_transport_type");
            throw exception;
        }
    }

    @Override
    public TransportType getByCourierId(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            TransportTypeExpandedDao dao = (TransportTypeExpandedDao) daoFactory.getDao(TransportType.class);
            return dao.getByCourierId(courierId);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get TransportType for courier", e);
            exception.setErrorKey("get_transport_type_for_courier");
            throw exception;
        }
    }

    @Override
    public List<TransportType> getAllTransportTypes() throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            return dao.getAll();
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with get all TransportTypes", e);
            exception.setErrorKey("all_transport_types");
            throw exception;
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
            ServiceException exception = new ServiceException("Problem with delete transport type by id", e);
            exception.setErrorKey("delete_transport_type");
            throw exception;
        }
    }

    @Override
    public void add(TransportType transportType) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            dao.persist(transportType);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with add transport with bane:", e);
            exception.setErrorKey("add_transport_type");
            throw exception;
        }
    }


    @Override
    public void edit(TransportType transportType) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<TransportType, Integer> dao = daoFactory.getDao(TransportType.class);
            dao.update(transportType);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with edit transport with bane!", e);
            exception.setErrorKey("edit_transport_type");
            throw exception;
        }
    }

    @Override
    public TransportType getByName(String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            TransportTypeExpandedDao dao = (TransportTypeExpandedDao) daoFactory.getDao(TransportType.class);
            return dao.getByName(name);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot find transport type by name!", e);
            exception.setErrorKey("get_transport_type_by_name");
            throw exception;
        }
    }
}
