package by.zinkov.victor.service;

import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.exception.ServiceException;

import java.util.List;

public interface TransportTypeService {
    List<TransportType> getAllTransportTypes() throws ServiceException;

    void delete(Integer transportTypeId) throws ServiceException;

    void add(String name) throws ServiceException;

    void edit(String id, String name)throws ServiceException;
}
