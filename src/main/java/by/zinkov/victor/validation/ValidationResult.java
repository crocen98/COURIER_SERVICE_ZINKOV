package by.zinkov.victor.validation;

import java.util.List;

public class ValidationResult {

    List<FieldValidationError> errors;


    public boolean isVaid() {
        return errors.isEmpty();
    }
}
