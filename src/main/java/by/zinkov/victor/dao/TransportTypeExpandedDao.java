package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.TransportType;

public interface TransportTypeExpandedDao {
    @AutoConnection
    TransportType getByName(String name) throws DaoException;
}
