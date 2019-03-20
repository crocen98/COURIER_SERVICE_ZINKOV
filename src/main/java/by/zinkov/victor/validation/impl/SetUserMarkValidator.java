package by.zinkov.victor.validation.impl;


import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class SetUserMarkValidator implements Validator {
    private static final String RATING_PARAMETER = "rating";
    private static final String COURIER_ID_PARAMETER = "courier_id";


    private static final String RATING_PARAMETER_ERROR = "validation.rating.error";
    private static final String COURIER_ID_PARAMETER_ERROR = "validation.not_valid_id.error";


    @Override
    public Map<String, String> validate(Map<String, String> parameters) {
        Map<String, String> errorsMap = new HashMap<>();
        UtilValidator validator = UtilValidator.getInstance();

        String mark = parameters.get(RATING_PARAMETER);
        if (!validator.isMatchesInt(mark, new int[]{2, 10})
                || Integer.valueOf(mark) % 2 != 0) {
            errorsMap.put(RATING_PARAMETER, RATING_PARAMETER_ERROR);
        }

        if (!validator.isMatchesInt(parameters.get(COURIER_ID_PARAMETER), UtilValidator.POSITIVE_RANGE)) {
            errorsMap.put(COURIER_ID_PARAMETER, COURIER_ID_PARAMETER_ERROR);
        }
        return errorsMap;
    }
}
