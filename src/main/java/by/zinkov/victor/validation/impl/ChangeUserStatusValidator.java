package by.zinkov.victor.validation.impl;

import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserStatusValidator implements Validator {
    private static final String USER_ID_PARAMETER = "user_id";

    private static final String VALIDATION_USER_ID_ERROR_KEY = "validation.user_id.error";
    private static final String NOT_FIND_USER_ID_ERROR_KEY = "validation.user_id.error";

    @Override
    public Map<String, String> validate(Map<String, String> parameters) throws ServiceException {
        UtilValidator validator = UtilValidator.getInstance();
        Map<String, String> errorsMap = new HashMap<>();
        String userId = parameters.get(USER_ID_PARAMETER);
        if (!validator.isMatchesInt(userId, UtilValidator.POSITIVE_RANGE)) {
            errorsMap.put(USER_ID_PARAMETER, VALIDATION_USER_ID_ERROR_KEY);
        } else {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            UserDto userDto = userService.getByPK(Integer.valueOf(userId));
            if (userDto == null) {
                errorsMap.put(USER_ID_PARAMETER, NOT_FIND_USER_ID_ERROR_KEY);
            }
        }
        return errorsMap;
    }
}
