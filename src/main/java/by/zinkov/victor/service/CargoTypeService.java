package by.zinkov.victor.service;

import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.service.exception.ServiceException;

import java.util.List;

public interface CargoTypeService {

    List<CargoType> getAllCargoTypes() throws ServiceException;
}
