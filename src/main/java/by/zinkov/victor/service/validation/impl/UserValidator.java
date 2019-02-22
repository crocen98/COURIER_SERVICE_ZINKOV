package by.zinkov.victor.service.validation.impl;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.EntityValidator;
import by.zinkov.victor.service.validation.StringValidator;

public class UserValidator implements EntityValidator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        StringValidator validator =  StringValidator.getInstance();
        validator.simpleStingMatches(entity.getFirstName(),45,"first name");
        validator.simpleStingMatches(entity.getLastName(),45,"last name");
        validator.simpleStingMatches(entity.getLogin(),45,"last name");
    }
}
