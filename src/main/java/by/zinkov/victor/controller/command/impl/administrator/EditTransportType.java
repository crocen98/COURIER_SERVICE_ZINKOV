package by.zinkov.victor.controller.command.impl.administrator;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.service.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class EditTransportType implements Command {
    private static final String ROUT_TO_DISPLAY_ALL_TRANSPORT_TYPES_PAGE = "/CourierCompany?command=all_cargo_types" ;
    private static final String TRANSPORT_NAME_PARAMETER = "transport_name";
    private static final String TRANSPORT_ID_PARAMETER = "transport_type_id";

    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory = new ServiceFactory();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        Router router = new Router();
        try {
            String transportName = request.getParameter(TRANSPORT_NAME_PARAMETER);
            String transportId = request.getParameter(TRANSPORT_ID_PARAMETER);
            service.edit(transportId,transportName);
            router.setRoute(ROUT_TO_DISPLAY_ALL_TRANSPORT_TYPES_PAGE);
            router.setType(Router.Type.REDIRECT);

        } catch (ServiceException e) {
            router.setRoute(ROUT_TO_DISPLAY_ALL_TRANSPORT_TYPES_PAGE + "&error=" + e.getMessage());
        }
        return router;
    }
}
