package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.CargoTypeExpandedDao;
import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.TransportTypeExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.exception.ServiceException;

import java.util.List;

public class CargoTypeServiceImpl implements CargoTypeService {


    @Override
    public CargoType getByName(String name) throws ServiceException{
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
            throw new ServiceException("Cannot get all cargo types!",e);
        }
    }
}
