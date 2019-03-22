package by.zinkov.victor.validation.impl;

import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateOrderFirstStageValidator implements Validator {
    private static final String DATETIME_PATTERN = "yyyy-MM-dd, HH:mm";

    private static final String CARGO_TYPE_PARAMETER = "cargo_type";
    private static final String TRANSPORT_TYPE_PARAMETER = "transport_type";
    private static final String START_POINT_PARAMETER = "start_point";
    private static final String FINISH_POINT_PARAMETER = "finish_point";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String START_TIME_PARAMETER = "start_time";

    private static final String CARGO_TYPE_ERROR_KEY = "validation.not_existing_cargo_type.error";
    private static final String TRANSPORT_TYPE_ERROR_KEY = "validation.not_existing_transport_type.error";
    private static final String POINT_ERROR_KEY = "validation.point.error";
    private static final String DESCRIPTION_ERROR_KEY = "validation.description.error";
    private static final String START_TIME_VALIDATION_ERROR_KEY = "validation.time_invalid.error";
    private static final String TIME_IN_PAST_ERROR_KEY = "validation.past_time.error";


    @Override
    public Map<String, String> validate(Map<String, String> requestParameters) {
        UtilValidator validator = UtilValidator.getInstance();
        Map<String, String> errorsMap = new HashMap<>();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TransportTypeService transportTypeService = serviceFactory.getTransportTypeService();
        CargoTypeService cargoTypeService = serviceFactory.getCargoTypeService();


        try {
            CargoType cargoType = cargoTypeService.getByName(requestParameters.get(CARGO_TYPE_PARAMETER));
            if (cargoType == null) {
                errorsMap.put(CARGO_TYPE_PARAMETER, CARGO_TYPE_ERROR_KEY);
            }
        } catch (ServiceException e) {
            throw new IllegalStateException("Problem with database", e);
        }


        try {
            TransportType transportType = transportTypeService.getByName(requestParameters.get(TRANSPORT_TYPE_PARAMETER));
            if (transportType == null) {
                errorsMap.put(TRANSPORT_TYPE_PARAMETER, TRANSPORT_TYPE_ERROR_KEY);
            }
        } catch (ServiceException e) {
            throw new IllegalStateException("Problem with database", e);
        }

        if (!validator.coordinatesMatches(requestParameters.get(START_POINT_PARAMETER))) {
            errorsMap.put(START_POINT_PARAMETER, POINT_ERROR_KEY);
        }


        if (!validator.coordinatesMatches(requestParameters.get(FINISH_POINT_PARAMETER))) {
            errorsMap.put(FINISH_POINT_PARAMETER, POINT_ERROR_KEY);
        }

        if (!validator.simpleStingMatches((requestParameters.get(DESCRIPTION_PARAMETER)), 150)) {
            errorsMap.put(DESCRIPTION_PARAMETER, DESCRIPTION_ERROR_KEY);
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
        Date date;
        if (requestParameters.get(START_TIME_PARAMETER) != null) {
            try {
                date = dateFormat.parse(requestParameters.get(START_TIME_PARAMETER));

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.HOUR_OF_DAY, 1);
                Date ourAfterOrder = cal.getTime();

                if (date.before(ourAfterOrder)) {
                    errorsMap.put(START_TIME_PARAMETER, TIME_IN_PAST_ERROR_KEY);
                }
            } catch (ParseException e) {
                errorsMap.put(START_TIME_PARAMETER, START_TIME_VALIDATION_ERROR_KEY);
            }
        } else {
            errorsMap.put(START_TIME_PARAMETER, START_TIME_VALIDATION_ERROR_KEY);

        }

        return errorsMap;
    }
}
