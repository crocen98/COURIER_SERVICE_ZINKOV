package by.zinkov.victor.service;

import by.zinkov.victor.domain.TransportType;

import java.util.List;

public interface TransportTypeService {
    List<TransportType> getAllTransportTypes() throws ServiceException;

    void delete(Integer transportTypeId) throws ServiceException;

    void add(String name) throws ServiceException;

    void edit(String id, String name)throws ServiceException;

    TransportType getByName(String name) throws ServiceException;
}
