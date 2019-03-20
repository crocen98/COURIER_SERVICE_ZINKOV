package by.zinkov.victor.validation.impl;

import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class DeleteByIdValidator implements Validator {

    private String idParameter;
    private String errorKey;

    public String getIdParameter() {
        return idParameter;
    }

    public void setIdParameter(String idParameter) {
        this.idParameter = idParameter;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    @Override
    public Map<String, String> validate(Map<String, String> parameters)  {
        UtilValidator utilValidator = UtilValidator.getInstance();
        Map<String, String> errorsMap = new HashMap<>();
        if (!utilValidator.isMatchesInt(parameters.get(idParameter), UtilValidator.POSITIVE_RANGE)) {
            errorsMap.put(idParameter, errorKey);
        }
        return errorsMap;
    }
}
