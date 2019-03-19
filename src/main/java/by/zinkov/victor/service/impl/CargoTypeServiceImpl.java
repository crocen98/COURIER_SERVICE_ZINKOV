package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.CargoTypeExpandedDao;
import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.ServiceException;

import java.util.List;

public class CargoTypeServiceImpl implements CargoTypeService {

    @Override
    public void update(CargoType cargoType) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CargoType, Integer> dao = daoFactory.getDao(CargoType.class);
            dao.update(cargoType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with delete cargo type: ", e);
        }
    }

    @Override
    public void deleteById(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CargoType, Integer> dao = daoFactory.getDao(CargoType.class);
            CargoType cargoType = dao.getByPK(id);
            dao.delete(cargoType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with delete cargo type: ", e);
        }
    }

    @Override
    public void add(CargoType cargoType) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CargoType, Integer> dao = daoFactory.getDao(CargoType.class);
            dao.persist(cargoType);
        } catch (DaoException e) {
            throw new ServiceException("Problem with add cargo type: ", e);
        }
    }

    @Override
    public CargoType getById(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();

        try {
            GenericDao<CargoType, Integer> dao = (GenericDao<CargoType, Integer>) daoFactory.getDao(CargoType.class);
            return dao.getByPK(id);
        } catch (DaoException e) {
            throw new ServiceException("Cannot find by id!", e);
        }
    }

    @Override
    public List<CargoType> getByCourierId(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();

        try {
            CargoTypeExpandedDao dao = (CargoTypeExpandedDao) daoFactory.getDao(CargoType.class);
            return dao.getByCourierId(courierId);
        } catch (DaoException e) {
            throw new ServiceException("Cannot find by courier id!", e);
        }
    }

    @Override
    public CargoType getByName(String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            CargoTypeExpandedDao dao = (CargoTypeExpandedDao) daoFactory.getDao(CargoType.class);
            return dao.getByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Cannot find by name!", e);
        }
    }

    @Override
    public List<CargoType> getAllCargoTypes() throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CargoType, Integer> dao = daoFactory.getDao(CargoType.class);
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("Cannot get all cargo types!", e);
        }
    }
}
