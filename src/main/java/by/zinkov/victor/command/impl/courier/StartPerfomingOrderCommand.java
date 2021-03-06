package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.OrderStatusService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class StartPerfomingOrderCommand extends Command {
    private static final String USER = "user";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.TO_COURIER_ACTIVE_ORDER_PAGE.getUrl());

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();
        OrderStatusService orderStatusService = serviceFactory.getOrderStatusService();
        Integer oldStatusId;
        try {
            Order order = orderService.getActiveOrderByCourierId(userDto.getId());
            if (null == order) {
                router.setRoute(CommandEnum.TO_COURIER_ACTIVE_ORDER_PAGE.getUrlWithError("error.cannot_have_order.order"));
                return router;
            }
            oldStatusId = order.getIdStatus();

            OrderStatus orderStatus = OrderStatus.PERFORMED;
            orderStatus = orderStatusService.getByName(orderStatus);
            order.setIdStatus(orderStatus.getId());
            orderService.update(order, oldStatusId);
        } catch (ServiceException e) {
            router.setRoute(CommandEnum.TO_COURIER_ACTIVE_ORDER_PAGE.getUrlWithError(e.getErrorKey()));
        }


        return router;
    }
}
