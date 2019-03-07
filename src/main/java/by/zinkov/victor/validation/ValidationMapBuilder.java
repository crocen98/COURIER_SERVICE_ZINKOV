package by.zinkov.victor.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationMapBuilder {
    private Map<String, String> validationMap = new HashMap<>();

    public ValidationMapBuilder addValidationParameters(String parameter, String errorKey) {
        validationMap.put(parameter, errorKey);
        return this;
    }

    public ValidationMapBuilder build(String[] parameters, String[] errorsKeys) {
        if (parameters == null || errorsKeys == null || parameters.length != errorsKeys.length) {
            throw new IllegalStateException("Not valid Arguments! Cannot create needed map!");
        }
        for (int i = 0; i < parameters.length; ++i) {
            validationMap.put(parameters[i], errorsKeys[i]);
        }

        return this;
    }

    public Map<String, String> getValidationMap() {
        return validationMap;
    }
}
