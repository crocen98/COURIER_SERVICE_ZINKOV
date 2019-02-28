package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.CargoType;

public interface CargoTypeExpandedDao {
    @AutoConnection
    CargoType getByName(String name) throws DaoException;
}
