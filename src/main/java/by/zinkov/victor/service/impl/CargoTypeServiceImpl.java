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
            ServiceException exception = new ServiceException("Problem with update cargo type:", e);
            exception.setErrorKey("update_cargo");
            throw exception;
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
            ServiceException exception = new ServiceException("Problem with delete cargo by id:" , e);
            exception.setErrorKey("delete_by_id_cargo");
            throw exception;
        }
    }

    @Override
    public void add(CargoType cargoType) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CargoType, Integer> dao = daoFactory.getDao(CargoType.class);
            dao.persist(cargoType);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Problem with add cargo type:", e);
            exception.setErrorKey("add_cargo");
            throw exception;
        }
    }

    @Override
    public CargoType getById(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();

        try {
            GenericDao<CargoType, Integer> dao = (GenericDao<CargoType, Integer>) daoFactory.getDao(CargoType.class);
            return dao.getByPK(id);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot find by id!", e);
            exception.setErrorKey("find_cargo_by_id");
            throw exception;
        }
    }

    @Override
    public List<CargoType> getByCourierId(Integer courierId) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();

        try {
            CargoTypeExpandedDao dao = (CargoTypeExpandedDao) daoFactory.getDao(CargoType.class);
            return dao.getByCourierId(courierId);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot find by courier id!", e);
            exception.setErrorKey("find_cargo_by_courier_id");
            throw exception;
        }
    }

    @Override
    public CargoType getByName(String name) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            CargoTypeExpandedDao dao = (CargoTypeExpandedDao) daoFactory.getDao(CargoType.class);
            return dao.getByName(name);
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot find by name!", e);
            exception.setErrorKey("find_cargo_by_name");
            throw exception;
        }
    }

    @Override
    public List<CargoType> getAllCargoTypes() throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<CargoType, Integer> dao = daoFactory.getDao(CargoType.class);
            return dao.getAll();
        } catch (DaoException e) {
            ServiceException exception = new ServiceException("Cannot get all cargo types!", e);
            exception.setErrorKey("get_all_cargos");
            throw exception;
        }
    }
}
