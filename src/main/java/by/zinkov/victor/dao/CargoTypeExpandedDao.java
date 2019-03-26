package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.CargoType;

import java.util.List;

public interface CargoTypeExpandedDao extends GenericDao<CargoType, Integer> {
    @AutoConnection
    CargoType getByName(String name) throws DaoException;

    @AutoConnection
    List<CargoType> getByCourierId(Integer courierId) throws DaoException;
}
