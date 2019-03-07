package by.zinkov.victor.validation;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class SignUpValidator implements Validator {

    private static int MAX_VARCHAR_DB_FIELD_LENGTH = 45;

    private static final String USER_ROLE_PARAMETER = "user_role";
    private static final String LOGIN_FIELD = "login";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String PASSWORD_FIELD = "password";
    private static final String LOCATION_FIELD = "location";

    private static final String ROLE_ERROR_KEY = "signup.rolenotvalid.error";
    private static final String LOGIN_ERROR_KEY = "signup.loginnotvalid.error";
    private static final String FIRST_NAME_ERROR_KEY = "signup.firstnamenotvalid.error";
    private static final String LAST_NAME_ERROR_KEY = "signup.lastnamenotvalid.error";
    private static final String EMAIL_ERROR_KEY = "signup.emailnotvalid.error";
    private static final String PHONE_ERROR_KEY = "signup.phonenotvalid.error";
    private static final String PASSWORD_ERROR_KEY = "signup.passwordnotvalid.error";
    private static final String LOCATION_ERROR_KEY = "signup.locationnotvalid.error";

    private static final String LOGIN_ALREADY_USE_ERROR_KEY = "error.logincheck";


    @Override
    public Map<String, String> validate(String... validationParameters) {
        if (validationParameters == null || validationParameters.length != 8) {
            throw new IllegalArgumentException("Count of arguments should be equals 8.");
        }
        Map<String, String> errorsMap = new HashMap<>();
        UtilValidator validator = UtilValidator.getInstance();

        if (!validator.simpleStingMatches(validationParameters[0], MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(USER_ROLE_PARAMETER, ROLE_ERROR_KEY);
        }

        if (!validator.simpleStingMatches(validationParameters[1], MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(LOGIN_FIELD, LOGIN_ERROR_KEY);
        }

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService service = factory.getUserService();

        if (validationParameters[1] != null) {
            User user;
            try {
                user = service.getByLogin(validationParameters[1]);
            } catch (ServiceException e) {
                throw new IllegalStateException("Problem with database", e);
            }
            if (user != null) {
                errorsMap.put(LOGIN_FIELD, LOGIN_ALREADY_USE_ERROR_KEY);
            }
        }

        if (!validator.simpleStingMatches(validationParameters[2], MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(FIRST_NAME_FIELD, FIRST_NAME_ERROR_KEY);
        }
        if (!validator.simpleStingMatches(validationParameters[3], MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(LAST_NAME_FIELD, LAST_NAME_ERROR_KEY);
        }
        if (!validator.emailMatches(validationParameters[4])) {
            errorsMap.put(EMAIL_FIELD, EMAIL_ERROR_KEY);
        }
        if (!validator.phoneMatches(validationParameters[5])) {
            errorsMap.put(PHONE_FIELD, PHONE_ERROR_KEY);
        }
        if (!validator.simpleStingMatches(validationParameters[6], MAX_VARCHAR_DB_FIELD_LENGTH)) {
            errorsMap.put(PASSWORD_FIELD, PASSWORD_ERROR_KEY);
        }
        if (!validator.coordinatesMatches(validationParameters[7])) {
            errorsMap.put(LOCATION_FIELD, LOCATION_ERROR_KEY);
        }
        return errorsMap;
    }
}
