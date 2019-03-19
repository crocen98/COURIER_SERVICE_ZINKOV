package by.zinkov.victor.validation.impl;

import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class AddTransportTypeValidator implements Validator {
    private static final String TRANSPORT_NAME_PARAMETER = "transport_name";
    private static final String COEFFICIENT_PARAMETER = "coefficient";

    private static final String TRANSPORT_NAME_ERROR = "transport_name.validation.error";
    private static final String COEFFICIENT_ERROR = "coefficient.validation.error";


    @Override
    public Map<String, String> validate(Map<String, String> requestParameters) {
        UtilValidator validator = UtilValidator.getInstance();

        Map<String, String> errorsMap = new HashMap<>();
        String transportName = requestParameters.get(TRANSPORT_NAME_PARAMETER);
        if (!validator.simpleStingMatches(transportName, 45)) {
            errorsMap.put(transportName, TRANSPORT_NAME_ERROR);
        }
        String coefficient = requestParameters.get(COEFFICIENT_PARAMETER);
        if (!validator.isMatchesDecimal(coefficient)) {
            errorsMap.put(transportName, COEFFICIENT_ERROR);
        }
        return errorsMap;
    }
}
