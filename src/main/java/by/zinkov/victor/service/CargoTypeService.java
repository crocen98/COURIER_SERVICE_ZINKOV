package by.zinkov.victor.service;

import by.zinkov.victor.domain.CargoType;

import java.util.List;

public interface CargoTypeService {

    CargoType getByName(String name) throws ServiceException;

    List<CargoType> getAllCargoTypes() throws ServiceException;
}
