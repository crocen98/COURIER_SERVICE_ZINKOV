package by.zinkov.victor.controller.command.impl.user;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.DistanceService;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.domain.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserOrderCommand implements Command {
    private static final String USER_ATTRIBUTE = "user";
    private static final String COURIER_ATTRIBUTE = "courier";
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String DISTANCE_ATTRIBUTE = "distance";


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.ORDER_PAGE.getRout());
        ServiceFactory factory = ServiceFactory.getInstance();
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto)session.getAttribute(USER_ATTRIBUTE);

        OrderService orderService = factory.getOrderService();
        UserService userService = factory.getUserService();
        try {
            Order order = orderService.getActiveOrderByClientId(userDto.getId());
            UserDto courier = userService.getByPK(order.getIdCourier());
            request.setAttribute(ORDER_ATTRIBUTE, order);
            request.setAttribute(COURIER_ATTRIBUTE,courier);
            double distance = calculateDistance(order);
            request.setAttribute(DISTANCE_ATTRIBUTE, distance);
        } catch (ServiceException e) {
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.TO_CREATE_ORDER_PAGE.getUrlWithError("createorderpage.error"));
        }


        return router;
    }


    private double calculateDistance(Order order){
        DistanceService service = new DistanceService();
        return service.calculate(order.getStartPoint(),order.getFinishPoint());
    }
}
