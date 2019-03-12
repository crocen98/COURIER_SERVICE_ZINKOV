package by.zinkov.victor.service;

import by.zinkov.victor.domain.SupportedCargoTypes;


public interface SupportedCargoTypeService {
    void add(SupportedCargoTypes cargoTypes) throws ServiceException;

    void deleteByCourierId(Integer courierId, Integer cargoTypeID) throws ServiceException;

}
