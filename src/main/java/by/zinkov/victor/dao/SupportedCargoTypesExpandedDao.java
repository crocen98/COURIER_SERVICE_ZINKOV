package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;

public interface SupportedCargoTypesExpandedDao {
    @AutoConnection
    void deleteByCourierId(Integer courierId, Integer cargoTypeID) throws DaoException;
}
