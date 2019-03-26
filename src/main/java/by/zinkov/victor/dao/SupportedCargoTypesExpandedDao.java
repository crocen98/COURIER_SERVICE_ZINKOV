package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.SupportedCargoTypes;

public interface SupportedCargoTypesExpandedDao extends GenericDao<SupportedCargoTypes, Integer> {
    @AutoConnection
    void deleteByCourierId(Integer courierId, Integer cargoTypeID) throws DaoException;
}
