package by.zinkov.victor.service;

import by.zinkov.victor.domain.TransportType;

import java.util.List;

public interface TransportTypeService {
    List<TransportType> getAllTransportTypes() throws ServiceException;

    void delete(Integer transportTypeId) throws ServiceException;

    void add(TransportType transportType) throws ServiceException;

    void edit(TransportType transportType)throws ServiceException;
    TransportType getByName(String name) throws ServiceException;

    TransportType getByCourierId(Integer courierId) throws ServiceException;
    TransportType getById(Integer pk) throws ServiceException;

}
