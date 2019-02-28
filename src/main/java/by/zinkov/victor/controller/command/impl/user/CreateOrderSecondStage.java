package by.zinkov.victor.controller.command.impl.user;
import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.OrderServiceImpl;
import by.zinkov.victor.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import  by.zinkov.victor.domain.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class CreateOrderSecondStage implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CreateOrderSecondStage.class);
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String CARGO_TYPE_PARAMETER = "cargo_type";
    private static final String TRANSPORT_TYPE_PARAMETER = "transport_type";
    private static final String COURIERS_ATTRIBUTE = "couriers";
    private static final String USER_ATTRIBUTE = "user";


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.CREATE_ORDER_SECOND_STAGE.getRout());
        String transportTypeString = request.getParameter(TRANSPORT_TYPE_PARAMETER);
        String cargoTypeString = request.getParameter(CARGO_TYPE_PARAMETER);

        OrderService service = new OrderServiceImpl();
        HttpSession session = request.getSession();
        try {
            Order order = service.createOrderFirstStage(request, transportTypeString , cargoTypeString);
            UserDto user = (UserDto)session.getAttribute(USER_ATTRIBUTE);

            order.setId(user.getId());
            order.setPrice(new BigDecimal(4));

            session.setAttribute(ORDER_ATTRIBUTE , order);
            UserService userService = new UserServiceImpl();
            List<User> couriers = userService.getCouriersByParams(transportTypeString,cargoTypeString);
            request.setAttribute(COURIERS_ATTRIBUTE,couriers);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(ORDER_ATTRIBUTE , null);
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.TO_CREATE_ORDER_PAGE.getUrlWithError("Cannot create order!"));
        }
        return router;
    }
}
