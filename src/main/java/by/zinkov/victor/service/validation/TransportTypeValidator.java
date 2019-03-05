package by.zinkov.victor.service.validation;

import by.zinkov.victor.domain.TransportType;


public class TransportTypeValidator implements EntityValidator<TransportType> {
    @Override
    public void validate(TransportType entity , String ... params)throws ValidationException {
        StringValidator validator = StringValidator.getInstance();
        validator.simpleStingMatches(entity.getTransportType(),45,"TransportType");
    }
}
