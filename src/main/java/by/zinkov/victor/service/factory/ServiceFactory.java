package by.zinkov.victor.service.factory;

import by.zinkov.victor.service.*;
import by.zinkov.victor.service.impl.*;

/**
 * Service factory
 */
public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public OrderService getOrderService() {
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

    public UserRoleService getUserRoleService() {
        return new UserRoleServiceImpl();
    }

    public UserStatusService getUserStatusService() {
        return new UserStatusServiceImpl();
    }

    public SupportedCargoTypeService getSupportedCargoTypeService(){
        return new SupportedCargoTypeServiceImpl();
    }

    public CargoTypeService getCargoTypeService() {
        return new CargoTypeServiceImpl();
    }

    public TransportTypeService getTransportTypeService() {
        return new TransportTypeServiceImpl();
    }

    public CustomerReviewsService getCustomerReviewsService() {
        return new CustomerReviewsServiceImpl();
    }

    public CurrierCapabilityService getCurrierCapabilityService() {
        return new CurrierCapabilityServiceImpl();
    }
    public DistanceService getDistanceService() {
        return new DistanceService();
    }

}
