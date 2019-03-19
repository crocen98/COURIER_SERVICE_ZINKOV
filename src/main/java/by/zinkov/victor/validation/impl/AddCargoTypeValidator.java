package by.zinkov.victor.validation.impl;

import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class AddCargoTypeValidator implements Validator {
    private static final String CARGO_TYPE_PARAMETER = "cargo_type";

    private static final String CARGO_TYPE_ERROR = "cargo_type.validation.error";


    @Override
    public Map<String, String> validate(Map<String, String> requestParameters) {
        UtilValidator validator = UtilValidator.getInstance();

        Map<String, String> errorsMap = new HashMap<>();
        String transportName = requestParameters.get(CARGO_TYPE_PARAMETER);
        if (!validator.simpleStingMatches(transportName, 45)) {
            errorsMap.put(transportName, CARGO_TYPE_ERROR);
        }
        return errorsMap;
    }
}
