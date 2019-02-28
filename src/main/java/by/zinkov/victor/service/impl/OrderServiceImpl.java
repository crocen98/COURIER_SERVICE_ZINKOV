package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.CargoTypeExpandedDao;
import by.zinkov.victor.dao.DaoFactory;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.TransportTypeExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.validation.StringValidator;
import by.zinkov.victor.util.RequestEntityBuilder;
import by.zinkov.victor.util.excepton.EntityFromRequestBuilderException;

import javax.servlet.http.HttpServletRequest;

public class OrderServiceImpl implements OrderService {
    private static final String USER_ATTRIBUTE = "user";

    @Override
    public Order createOrderFirstStage(HttpServletRequest request, String transportTypeString, String cargoTypeString) throws ServiceException {
        RequestEntityBuilder builder = new RequestEntityBuilder();
        try {
            Order order = builder.build(request, Order.class);
            StringValidator validator = StringValidator.getInstance();
            validator.coordinatesMatches(order.getStartPoint());
            validator.coordinatesMatches(order.getFinishPoint());
            validator.simpleStingMatches(order.getDescription(), 150, "description");
            validator.simpleStingMatches(transportTypeString, 45, "transportType");
            validator.simpleStingMatches(cargoTypeString, 45, "cargoType");
            // time validator


//            DaoFactory daoFactory = JdbcDaoFactory.getInstance();
//            CargoTypeExpandedDao cargoTypeExpandedDao = (CargoTypeExpandedDao) daoFactory.getDao(CargoType.class);
//            TransportTypeExpandedDao transportTypeExpandedDao = (TransportTypeExpandedDao) daoFactory.getDao(TransportType.class);
//
//            CargoType cargoType = cargoTypeExpandedDao.getByName(cargoTypeString);
//            TransportType transportType = transportTypeExpandedDao.getByName(transportTypeString);


            // order.setIdStatus();

            UserDto user = (UserDto) request.getSession().getAttribute(USER_ATTRIBUTE);
            order.setIdCustomer(user.getId());
            return order;
        } catch (EntityFromRequestBuilderException e) {
            throw new ServiceException("Problem with creating entity from request");
        } catch (ValidationException e) {
            throw new ServiceException("Problem with validation entity from request");
        }
    }

    @Override
    public void save(Order order) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            GenericDao<Order, Integer> dao = daoFactory.getDao(Order.class);
            dao.persist(order);
        } catch (DaoException e) {
            throw new ServiceException("Cannot save order!", e);
        }
    }
}

