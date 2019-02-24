package by.zinkov.victor.service;

import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.service.exception.ServiceException;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getAll() throws ServiceException;
    UserRole getByName(String name) throws ServiceException;
}
