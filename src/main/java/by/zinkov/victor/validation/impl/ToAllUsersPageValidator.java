package by.zinkov.victor.validation.impl;

import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class ToAllUsersPageValidator implements Validator {
    private static final String PAGE_PARAMETER = "page";

    private static final String PAGE_ERROR = "validation.page.error";
    @Override
    public Map<String, String> validate(Map<String, String> requestParameters) {
        Map<String, String> errorsMap = new HashMap<>();
        UtilValidator validator = UtilValidator.getInstance();
        if(!validator.isMatchesInt(requestParameters.get(PAGE_PARAMETER), new int[]{Integer.MIN_VALUE , Integer.MAX_VALUE})){
            errorsMap.put(PAGE_PARAMETER,PAGE_ERROR);
        }
        return errorsMap;
    }
}
