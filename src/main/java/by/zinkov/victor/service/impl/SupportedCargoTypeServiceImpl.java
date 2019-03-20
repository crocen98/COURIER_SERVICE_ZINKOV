package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.SupportedCargoTypesExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.SupportedCargoTypes;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.SupportedCargoTypeService;


public class SupportedCargoTypeServiceImpl implements SupportedCargoTypeService {


    @Override
    public void deleteByCourierId(Integer courierId, Integer cargoTypeID) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            SupportedCargoTypesExpandedDao dao = (SupportedCargoTypesExpandedDao) daoFactory.getDao(SupportedCargoTypes.class);
            dao.deleteByCourierId(courierId, cargoTypeID);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with delete cargoType for courier!", e);
            exception.setErrorKey("delete_supported_cargo_type");
            throw exception;
        }
    }

    @Override
    public void add(SupportedCargoTypes cargoTypes) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<SupportedCargoTypes, Integer> registrationKeyDao = daoFactory.getDao(SupportedCargoTypes.class);
            registrationKeyDao.persist(cargoTypes);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with save cargoType for courier!", e);
            exception.setErrorKey("save_supported_cargo_type");
            throw exception;
        }
    }
}
