package by.zinkov.victor.service;

import by.zinkov.victor.domain.CurrierCapability;

public interface CurrierCapabilityService {

    CurrierCapability getByCourierId(Integer courierId) throws ServiceException;
    CurrierCapability create(CurrierCapability currierCapability) throws ServiceException;
    void update(CurrierCapability currierCapability) throws ServiceException;
}
