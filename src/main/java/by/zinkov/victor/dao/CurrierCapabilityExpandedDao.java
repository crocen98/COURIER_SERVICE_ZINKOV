package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.CurrierCapability;

public interface CurrierCapabilityExpandedDao extends GenericDao<CurrierCapability, Integer>{
    @AutoConnection
    CurrierCapability getByCourierId(Integer courierId) throws DaoException;
}
