package by.zinkov.victor.command.impl.user;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.OrderDto;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.ToAllUsersPageValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class ToAllOrderPageCommand extends Command {
    private static final String ORDERS_ATTRIBUTE = "orders";
    private static final String PAGE_PARAMETER = "page";

    private static final String ORDERS_COUNT = "orders_count";
    private static final String ERRORS = "errors";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.ALL_USERS_ORDER.getRout());
        Map<String, String> parameters = super.readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ToAllUsersPageValidator validator = validatorFactory.getToAllUsersPageValidator();
        Map<String, String> errorsMap = validator.validate(parameters);
        if (errorsMap.size() != 0) {
            request.setAttribute(ERRORS, errorsMap);
            router.setRoute(Router.INDEX_ROUT);
            router.setType(Router.Type.FORWARD);
            return router;
        }

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();
        List<OrderDto> orders;
        try {
            int usersCount = orderService.getUsersOrdersCount(userDto.getId());
            request.setAttribute(ORDERS_COUNT,usersCount);
            int page;
            page = Integer.valueOf(parameters.get(PAGE_PARAMETER));
            orders = orderService.getAllUsersOrders((page-1)*20, userDto.getId());
        } catch (ServiceException e) {
            throw new CommandException("Command exception", e);
        }
        request.setAttribute(ORDERS_ATTRIBUTE, orders);
        return router;
    }
}
