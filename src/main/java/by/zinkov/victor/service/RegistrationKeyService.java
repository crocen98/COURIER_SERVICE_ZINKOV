package by.zinkov.victor.service;

import by.zinkov.victor.domain.RegistrationKey;

public interface RegistrationKeyService {
     void add(Integer id, String randomString) throws ServiceException;
     RegistrationKey getById(Integer id) throws ServiceException;
     void remove(RegistrationKey key) throws ServiceException;
}
