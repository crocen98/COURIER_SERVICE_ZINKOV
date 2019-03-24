package by.zinkov.victor.validation.impl;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class SignUpValidator implements Validator {

    private static int MAX_VARCHAR_DB_FIELD_LENGTH = 35;

    private static final String USER_ROLE_PARAMETER = "user_role";
    private static final String LOGIN_FIELD = "login";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String PASSWORD_FIELD = "password";
    private static final String LOCATION_FIELD = "location";
    private static final String PASSWORD_HASH_PARAMETER = "password_hash";


    private static final String ROLE_ERROR_KEY = "validation.role.error";
    private static final String LOGIN_ERROR_KEY = "validation.login.error";
    private static final String FIRST_NAME_ERROR_KEY = "validation.first_name.error";
    private static final String LAST_NAME_ERROR_KEY = "validation.last_name.error";
    private static final String EMAIL_ERROR_KEY = "validation.email.error";
    private static final String PHONE_ERROR_KEY = "validation.phone.error";
    private static final String PASSWORD_ERROR_KEY = "validation.password.error";
    private static final String LOCATION_ERROR_KEY = "validation.point.error";
    private static final String HASH_ERROR_KEY = "validation.hash.error";


    private static final String LOGIN_ALREADY_USE_ERROR_KEY = "validation.login_use.error";


    @Override
    public Map<String, String> validate(Map<String, String> parameters) {
        UtilValidator validator = UtilValidator.getInstance();
        Map<String, String> errorsMap = new HashMap<>();
        if (!validator.simpleStingMatches(parameters.get(USER_ROLE_PARAMETER), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(USER_ROLE_PARAMETER, ROLE_ERROR_KEY);
        }
        if (!validator.simpleStingMatches(parameters.get(LOGIN_FIELD), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(LOGIN_FIELD, LOGIN_ERROR_KEY);
        }

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService service = factory.getUserService();

        User user;
        try {
            user = service.getByLogin(parameters.get(LOGIN_FIELD));
        } catch (ServiceException e) {
            throw new IllegalStateException("Problem with database", e);
        }
        if (user != null) {
            errorsMap.put(LOGIN_FIELD, LOGIN_ALREADY_USE_ERROR_KEY);
        }

        if (!validator.simpleStingMatches(parameters.get(FIRST_NAME_FIELD), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(FIRST_NAME_FIELD, FIRST_NAME_ERROR_KEY);
        }
        if (!validator.simpleStingMatches(parameters.get(LAST_NAME_FIELD), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(LAST_NAME_FIELD, LAST_NAME_ERROR_KEY);
        }
        if (!validator.emailMatches(parameters.get(EMAIL_FIELD))) {
            errorsMap.put(EMAIL_FIELD, EMAIL_ERROR_KEY);
        }
        if (!validator.phoneMatches(parameters.get(PHONE_FIELD))) {
            errorsMap.put(PHONE_FIELD, PHONE_ERROR_KEY);
        }
        if (!validator.simpleStingMatches(parameters.get(PASSWORD_FIELD), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(PASSWORD_FIELD, PASSWORD_ERROR_KEY);
        } else {
            if (!validator.passwordHashMatches(parameters.get(PASSWORD_HASH_PARAMETER))) {
                errorsMap.put(PASSWORD_HASH_PARAMETER, HASH_ERROR_KEY);
            }
        }
        if (!validator.coordinatesMatches(parameters.get(LOCATION_FIELD))) {
            errorsMap.put(LOCATION_FIELD, LOCATION_ERROR_KEY);
        }

        return errorsMap;
    }

}
