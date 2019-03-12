package by.zinkov.victor.service;

import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;

public interface UserStatusService {
    UserStatus getByName(String name) throws ServiceException;
}
