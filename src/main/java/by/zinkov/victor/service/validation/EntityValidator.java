package by.zinkov.victor.service.validation;

import by.zinkov.victor.dao.Identified;
import by.zinkov.victor.service.exception.ValidationException;

public interface EntityValidator<T extends Identified<Integer>> {
    void validate(T entity) throws ValidationException;

}
