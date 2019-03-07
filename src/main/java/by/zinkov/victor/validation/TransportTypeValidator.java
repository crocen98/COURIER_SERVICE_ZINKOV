package by.zinkov.victor.validation;

import by.zinkov.victor.domain.TransportType;


public class TransportTypeValidator implements EntityValidator<TransportType> {
    @Override
    public void validate(TransportType entity , String ... params)throws ValidationException {
        UtilValidator validator = UtilValidator.getInstance();
        validator.simpleStingMatches(entity.getTransportType(),45/*,"TransportType"*/);
    }
}
