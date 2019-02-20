package by.zinkov.victor.service;

import by.zinkov.victor.service.impl.TransportTypeServiceImpl;
import by.zinkov.victor.service.impl.UserServiceImpl;

/**
 * Service factory
 */
public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return new UserServiceImpl();
    }

    public TransportTypeService getTransportTypeServiceImpl() {
        return new TransportTypeServiceImpl();
    }
}
