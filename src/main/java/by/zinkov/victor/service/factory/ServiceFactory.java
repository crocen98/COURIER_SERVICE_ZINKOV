package by.zinkov.victor.service.factory;

import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.OrderStatusService;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.impl.OrderServiceImpl;
import by.zinkov.victor.service.impl.OrderStatusServiceImpl;
import by.zinkov.victor.service.impl.TransportTypeServiceImpl;
import by.zinkov.victor.service.impl.UserServiceImpl;

/**
 * Service factory
 */
public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    private ServiceFactory(){}
    public static ServiceFactory getInstance() {
        return instance;
    }

    public OrderService getOrderService(){
        return new OrderServiceImpl();
    }
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    public TransportTypeService getTransportTypeServiceImpl() {
        return new TransportTypeServiceImpl();
    }

    public OrderStatusService getOrderStatusService() {
        return new OrderStatusServiceImpl();
    }


}
