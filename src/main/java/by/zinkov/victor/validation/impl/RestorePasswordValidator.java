package by.zinkov.victor.validation.impl;

import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class RestorePasswordValidator implements Validator {
    private static int MAX_VARCHAR_DB_FIELD_LENGTH = 35;
    private static final String USER_ID = "user_id";
    private static final String PASSWORD = "password";
    private static final String ACTIVATE_STRING = "key";
    private static final String PASSWORD_HASH_PARAMETER = "password_hash";



    private static final String NOT_MATCHES_NUMBER_KEY = "validation.not_valid_id.error";
    private static final String PASSWORD_ERROR_KEY = "validation.password.error";
    private static final String ACTIVATE_STRING_ERROR_KEY = "validation.key.error";
    private static final String HASH_ERROR_KEY = "validation.hash.error";

    @Override
    public Map<String, String> validate(Map<String, String> parameters) {
        UtilValidator validator = UtilValidator.getInstance();
        Map<String, String> errorsMap = new HashMap<>();

        if(!validator.isMatchesInt(parameters.get(USER_ID),UtilValidator.POSITIVE_RANGE)){
            errorsMap.put(USER_ID,NOT_MATCHES_NUMBER_KEY);
        }

        if (!validator.simpleStingMatches(parameters.get(PASSWORD), MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(PASSWORD, PASSWORD_ERROR_KEY);
        } else {
            if (!validator.passwordHashMatches(parameters.get(PASSWORD_HASH_PARAMETER))) {
                errorsMap.put(PASSWORD_HASH_PARAMETER, HASH_ERROR_KEY);
            }
        }

        String activateString = parameters.get(ACTIVATE_STRING);
        if (activateString != null && activateString.length() != 32) {
            errorsMap.put(ACTIVATE_STRING, ACTIVATE_STRING_ERROR_KEY);
        }
        return errorsMap;
    }
}
