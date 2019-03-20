package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.*;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToCourierActiveOrderPage extends Command {
    private static final String USER_ATTRIBUTE = "user";
    private static final String DISTANCE_ATTRIBUTE = "distance";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String TRANSPORT_TYPE_ATTRIBUTE = "transport_type";
    private static final String CARGO_TYPE_ATTRIBUTE = "cargo_type";
    private static final String ORDER_STATUS = "order_status";

    private static final Logger LOGGER = LogManager.getLogger(ToCourierActiveOrderPage.class);

    @Override
    public Router execute(HttpServletRequest request) {


        Router router = new Router();
        router.setRoute(Page.COURIER_ORDER.getRout());
        router.setType(Router.Type.FORWARD);

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER_ATTRIBUTE);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();
        DistanceService distanceService = serviceFactory.getDistanceService();
        UserService userService = serviceFactory.getUserService();
        CargoTypeService cargoTypeService = serviceFactory.getCargoTypeService();
        TransportTypeService transportTypeService = serviceFactory.getTransportTypeService();
        OrderStatusService orderStatusService = serviceFactory.getOrderStatusService();

        try {
            Order order = orderService.getActiveOrderByCourierId(userDto.getId());
            if(order == null){
                return router;
            }
            request.setAttribute(ORDER_ATTRIBUTE, order);
            double distance = distanceService.calculate(order.getStartPoint(), order.getFinishPoint());
            request.setAttribute(DISTANCE_ATTRIBUTE, distance);

            UserDto client = userService.getByPK(order.getIdCustomer());
            request.setAttribute(CLIENT_ATTRIBUTE, client);

            TransportType transportType = transportTypeService.getById(order.getIdTransportType());
            request.setAttribute(TRANSPORT_TYPE_ATTRIBUTE, transportType);

            CargoType cargoType = cargoTypeService.getById(order.getIdCargoType());
            request.setAttribute(CARGO_TYPE_ATTRIBUTE, cargoType);

            OrderStatus orderStatus = orderStatusService.getById(order.getIdStatus());

            request.setAttribute(ORDER_STATUS, orderStatus);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(Router.INDEX_ERROR_ROUT + "error with active order!");
            router.setType(Router.Type.REDIRECT);
        }
        return router;
    }
}
