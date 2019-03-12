package by.zinkov.victor.validation.impl;

import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class NewTransportTypeForCourierValidator implements Validator {
    private static final String TRANSPORT_TYPE_ID_PARAMETER = "transport_type_id";
    private static final String NOT_MATCHES_NUMBER_KEY = "validation.notmatchespositivint.error";
    private static final String CANNOT_FIND_TRANSPORT = "validation.cannotfindtransort.error";

    @Override
    public Map<String, String> validate(Map<String, String> requestParameters) throws ServiceException {
        Map<String, String> errorMap = new HashMap<>();
        UtilValidator validator = UtilValidator.getInstance();
        if(!validator.isMatchesInt(requestParameters.get(TRANSPORT_TYPE_ID_PARAMETER), UtilValidator.POSITIVE_RANGE)){
            errorMap.put(TRANSPORT_TYPE_ID_PARAMETER , NOT_MATCHES_NUMBER_KEY);
        } else {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TransportTypeService transportTypeService = serviceFactory.getTransportTypeService();
            TransportType transportType = transportTypeService.getById(Integer.valueOf(requestParameters.get(TRANSPORT_TYPE_ID_PARAMETER)));
            if(transportType == null){
                errorMap.put(TRANSPORT_TYPE_ID_PARAMETER, CANNOT_FIND_TRANSPORT);
            }
        }
        return errorMap;
    }
}
