package by.zinkov.victor.service.validation.impl;

import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.EntityValidator;

public class TransportTypeValidator implements EntityValidator<TransportType> {
    @Override
    public void validate(TransportType entity)throws ValidationException {
        String type = entity.getTransportType();
        if(type == null || type.length() > 45 || type.trim().equals("")){
          throw new ValidationException("not valid type :"  + type);
        }
    }
}
