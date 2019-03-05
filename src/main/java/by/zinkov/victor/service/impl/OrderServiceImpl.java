package by.zinkov.victor.service.impl;

import by.zinkov.victor.dao.*;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.validation.ValidationException;
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
    public Order getActiveOrderByClientId(Integer id) throws ServiceException {
        DaoFactory daoFactory = JdbcDaoFactory.getInstance();
        try {
            OrderExpandedDao dao = (OrderExpandedDao)daoFactory.getDao(Order.class);
            return dao.getActiveOrder(id);
        } catch (DaoException e) {
            throw new ServiceException("Cannot save order!", e);
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

