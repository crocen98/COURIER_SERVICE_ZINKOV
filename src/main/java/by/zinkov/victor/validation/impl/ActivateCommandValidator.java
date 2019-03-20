package by.zinkov.victor.validation.impl;

import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.Validator;
import by.zinkov.victor.validation.ValidatorFactory;

import java.util.Map;

public class ActivateCommandValidator implements Validator {
    private static final String ACTIVATE_STRING = "value";

    private static final String VALIDATION_ACTIVATE_STRING_ERROR_KEY = "validation.activate_string.error";

    @Override
    public Map<String, String> validate(Map<String, String> parameters) throws ServiceException {
        ValidatorFactory factory = ValidatorFactory.getInstance();

        ChangeUserStatusValidator userStatusValidator = factory.getChangeUserStatusValidator();
        Map<String, String> errorsMap = userStatusValidator.validate(parameters);

        String activateString = parameters.get(ACTIVATE_STRING);
        if (activateString != null && activateString.length() != 32) {
            errorsMap.put(ACTIVATE_STRING, VALIDATION_ACTIVATE_STRING_ERROR_KEY);
        }

        return errorsMap;
    }
}
