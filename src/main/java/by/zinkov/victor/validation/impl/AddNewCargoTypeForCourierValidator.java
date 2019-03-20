package by.zinkov.victor.validation.impl;

import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class AddNewCargoTypeForCourierValidator implements Validator {
    private static final String CARGO_TYPE_ID_PARAMETER = "cargo_type_id";
    private static final String NOT_MATCHES_NUMBER_KEY = "validation.not_valid_id.error";

    @Override
    public Map<String, String> validate(Map<String, String> requestParameters) {
        Map<String, String> errorMap = new HashMap<>();
        UtilValidator validator = UtilValidator.getInstance();
        if (!validator.isMatchesInt(requestParameters.get(CARGO_TYPE_ID_PARAMETER), UtilValidator.POSITIVE_RANGE)) {
            errorMap.put(CARGO_TYPE_ID_PARAMETER, NOT_MATCHES_NUMBER_KEY);
        }
        return errorMap;
    }
}

