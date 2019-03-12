package by.zinkov.victor.validation;


import by.zinkov.victor.service.ServiceException;

import java.util.Map;

public interface Validator {
    Map<String, String> validate(Map<String, String> requestParameters) throws ServiceException;
}
