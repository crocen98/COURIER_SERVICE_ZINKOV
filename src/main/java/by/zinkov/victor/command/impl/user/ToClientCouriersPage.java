package by.zinkov.victor.command.impl.user;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.exception.CommandException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.CustomerReviewsService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToClientCouriersPage implements Command {
    private static final String USER = "user";
    private static final String COURIERS = "couriers";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setRoute(Page.CLIENT_COURIERS.getRout());
        router.setType(Router.Type.FORWARD);

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        CustomerReviewsService customerReviewsService = serviceFactory.getCustomerReviewsService();
        List<User> couriers;
        Map<User, Double> map = new HashMap();
        try {
            couriers = userService.getClientCouriers(userDto.getId());
            for (User courier : couriers) {
                map.put(courier, customerReviewsService.getCourierMark(courier.getId()));
            }


        } catch (ServiceException e) {
            throw new CommandException("Error in ToClientCouriersPage command", e);
        }
        request.setAttribute(COURIERS, map);
        return router;
    }
}
