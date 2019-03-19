package by.zinkov.victor.service;

import by.zinkov.victor.domain.CargoType;

import java.util.List;

public interface CargoTypeService {

    CargoType getByName(String name) throws ServiceException;

    List<CargoType> getAllCargoTypes() throws ServiceException;
    List<CargoType> getByCourierId(Integer courierId) throws ServiceException;
    CargoType getById(Integer id) throws ServiceException;
    void add(CargoType cargoType) throws ServiceException;
    void deleteById(Integer id) throws ServiceException;
    void update(CargoType cargoType) throws ServiceException;

}
