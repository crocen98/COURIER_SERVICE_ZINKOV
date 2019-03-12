package by.zinkov.victor.validation.impl;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class SendRestorePasswordTokenValidator implements Validator {
    private static final Integer MAX_VARCHAR_DB_FIELD_LENGTH = 45;

    private static final String LOGIN_PARAMETER = "login";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PHONE_PARAMETER = "phone";

    private static final String LOGIN_ERROR = "signup.loginnotvalid.error";
    private static final String LOGIN_DONT_USE_ERROR = "restorepasswor.logindontuse.error";

    private static final String EMAIL_ERROR = "signup.emailnotvalid.error";
    private static final String PHONE_ERROR = "signup.phonenotvalid.error";

    @Override
    public Map<String, String> validate(Map<String, String> parameters) {
        UtilValidator validator = UtilValidator.getInstance();
        Map<String,String> errorsMap = new HashMap<>();

        if (!validator.simpleStingMatches(parameters.get(LOGIN_PARAMETER), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(LOGIN_PARAMETER, LOGIN_ERROR);
        } else {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService =serviceFactory.getUserService();
            try {
                User user = userService.getByLogin(LOGIN_PARAMETER);
                if(user == null){
                    errorsMap.put(LOGIN_PARAMETER,LOGIN_DONT_USE_ERROR);
                }
            } catch (ServiceException e) {
                throw new IllegalStateException("Problem in database!");
            }
        }

        if (!validator.simpleStingMatches(parameters.get(EMAIL_PARAMETER), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(EMAIL_PARAMETER, EMAIL_ERROR);
        }
        if (!validator.simpleStingMatches(parameters.get(PHONE_PARAMETER), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(PHONE_PARAMETER, PHONE_ERROR);
        }
        return errorsMap;

    }
}
