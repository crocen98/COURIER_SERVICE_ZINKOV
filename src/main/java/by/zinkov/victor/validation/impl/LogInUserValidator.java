package by.zinkov.victor.validation.impl;

import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class LogInUserValidator implements Validator {
    private static int MAX_VARCHAR_DB_FIELD_LENGTH = 35;

    private static final String LOGIN_FIELD = "login";
    private static final String PASSWORD_FIELD = "password";
    private static final String PASSWORD_HASH_PARAMETER = "password_hash";

    private static final String LOGIN_ERROR_KEY = "validation.login.error";
    private static final String PASSWORD_ERROR_KEY = "validation.password.error";
    private static final String HASH_ERROR_KEY = "validation.hash.error";


    @Override
    public Map<String, String> validate(Map<String, String> parameters) {
        UtilValidator validator = UtilValidator.getInstance();
        Map<String, String> errorsMap = new HashMap<>();
        if (!validator.simpleStingMatches(parameters.get(LOGIN_FIELD), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(LOGIN_FIELD, LOGIN_ERROR_KEY);
        }

        if (!validator.simpleStingMatches(parameters.get(PASSWORD_FIELD), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(PASSWORD_FIELD, PASSWORD_ERROR_KEY);
        } else {
            if (!validator.passwordHashMatches(parameters.get(PASSWORD_HASH_PARAMETER))) {
                errorsMap.put(PASSWORD_HASH_PARAMETER, HASH_ERROR_KEY);
            }
        }
        return errorsMap;
    }
}
