package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.TransportType;

public interface TransportTypeExpandedDao extends GenericDao<TransportType, Integer>{
    @AutoConnection
    TransportType getByCourierId(Integer courierId) throws DaoException;

    @AutoConnection
    TransportType getByName(String name) throws DaoException;
}
