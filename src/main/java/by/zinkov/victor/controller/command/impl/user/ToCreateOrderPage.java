package by.zinkov.victor.controller.command.impl.user;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.controller.command.impl.LogInUserCommand;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.OrderStatusService;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.CargoTypeServiceImpl;
import by.zinkov.victor.service.impl.OrderStatusServiceImpl;
import by.zinkov.victor.service.impl.TransportTypeServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToCreateOrderPage implements Command {
    private static final String USER_ATTRIBUTE = "user";
    private static final Logger LOGGER = LogManager.getLogger(ToCreateOrderPage.class);

    @Override
    public Router execute(HttpServletRequest request)  {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.CREATE_ORDER.getRout());

        TransportTypeService transportTypeService = new TransportTypeServiceImpl();
        CargoTypeService cargoTypeService = new CargoTypeServiceImpl();
        HttpSession session = request.getSession();
        UserDto user = (UserDto)session.getAttribute(USER_ATTRIBUTE);
        OrderStatusService orderStatusService = new OrderStatusServiceImpl();
        try {
            if (orderStatusService.haveActiveOrder(user.getId())){
                router.setType(Router.Type.REDIRECT);
                router.setRoute(CommandEnum.TO_USER_ORDER_PAGE.getUrlWithError("orderpage.error"));
                return router;
            }
            List<TransportType> transportTypes = transportTypeService.getAllTransportTypes();
            List<CargoType> cargoTypes = cargoTypeService.getAllCargoTypes();

            request.setAttribute("transport_types" , transportTypes);
            request.setAttribute("cargo_types" , cargoTypes);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(Router.INDEX_ERROR_ROUT + "Cannot load creating order page");
            router.setType(Router.Type.REDIRECT);
        }
        return router;
    }
}
