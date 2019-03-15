package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.CustomerReviewsService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToEditProfilePageCommand implements Command {

    private static final String USER_ATTRIBUTE = "user";
    private static final String TRANSPORT_TYPE_ATTRIBUTE = "transport_type";
    private static final String TRANSPORT_TYPES_ATTRIBUTE = "transport_types";
    private static final String CARGO_TYPES_ATTRIBUTE = "cargo_types";
    private static final String ALL_CARGO_TYPES_ATTRIBUTE = "all_cargo_types";

    private static final String MARK_ATTRIBUTE = "mark";




    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setRoute(Page.COURIER_PROFILE.getRout());
        router.setType(Router.Type.FORWARD);

        HttpSession session = request.getSession();


        UserDto user = (UserDto) session.getAttribute(USER_ATTRIBUTE);
        Integer id = user.getId();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        TransportTypeService transportTypeService = serviceFactory.getTransportTypeService();
        CargoTypeService cargoTypeService = serviceFactory.getCargoTypeService();
        CustomerReviewsService customerReviewsService = serviceFactory.getCustomerReviewsService();
        try {
            TransportType transportType = transportTypeService.getByCourierId(id);
            List<CargoType> cargoTypes = cargoTypeService.getByCourierId(id);
            List<CargoType> allCargoTypes = cargoTypeService.getAllCargoTypes();

            Double courierMark = customerReviewsService.getCourierMark(id);
            List<TransportType> transportTypes = transportTypeService.getAllTransportTypes();
            request.setAttribute(TRANSPORT_TYPE_ATTRIBUTE,transportType);
            request.setAttribute(CARGO_TYPES_ATTRIBUTE, cargoTypes);
            request.setAttribute(MARK_ATTRIBUTE, courierMark);
            request.setAttribute(TRANSPORT_TYPES_ATTRIBUTE, transportTypes);
            request.setAttribute(ALL_CARGO_TYPES_ATTRIBUTE, allCargoTypes);

        } catch (ServiceException e) {
            router.setRoute(Router.INDEX_ERROR_ROUT + " problem! Try again!");
            router.setType(Router.Type.REDIRECT);
        }
        return router;

    }
}
