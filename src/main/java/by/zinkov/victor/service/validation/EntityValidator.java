package by.zinkov.victor.service.validation;

import by.zinkov.victor.dao.Identified;

public interface EntityValidator<T extends Identified<Integer>> {
    void validate(T entity , String ... otherParams) throws ValidationException;

}
