package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CancelOrderCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(CancelOrderCommand.class);

    private static final String USER_ATTRIBUTE = "user";
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRoute(Router.INDEX_ROUT);
        router.setType(Router.Type.REDIRECT);

        HttpSession session = request.getSession();
        session.getAttribute(USER_ATTRIBUTE);

        UserDto userDto = (UserDto) session.getAttribute(USER_ATTRIBUTE);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();

        try {
            orderService.cancelOrder(userDto.getId(),userDto.getUserRole());
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(Router.INDEX_ERROR_ROUT + e.getMessage());
        }
        return router;
    }
}
