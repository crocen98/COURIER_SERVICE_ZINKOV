package by.zinkov.victor.service.validation;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;


import java.util.Arrays;

public class UserValidator implements EntityValidator<User> {
    @Override
    public void validate(User entity, String... otherParams) throws ValidationException {
        if (otherParams == null || otherParams.length != 1) {
            throw new IllegalStateException("Unsupported parameters count!");
        }
        StringValidator validator = StringValidator.getInstance();
        validator.simpleStingMatches(entity.getFirstName(), 45, "first name");
        validator.simpleStingMatches(entity.getLastName(), 45, "last name");
        validator.simpleStingMatches(entity.getLogin(), 45, "login");
        validator.simpleStingMatches(entity.getPassword(), 45, "password");
        validator.emailMatches(entity.getEmail());
        validator.phoneMatches(entity.getPhone());
        validator.coordinatesMatches(entity.getLocation());


        if (Arrays.stream(UserRole.values())
                .map(UserRole::toString)
                .noneMatch(param ->
                        otherParams[0].equalsIgnoreCase(param)
                )) {
            throw new ValidationException("cannot supported enum " + otherParams[0]);

        }
    }
}
