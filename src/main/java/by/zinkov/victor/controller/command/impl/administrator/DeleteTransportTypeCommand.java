package by.zinkov.victor.controller.command.impl.administrator;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.service.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteTransportTypeCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTransportTypeCommand.class);

    private static final String TRANSPORT_TYPE_ID_PARAMETER = "transport_type_id";
    private static final String ROUT_TO_DISPLAY_ALL_TRANSPORT_TYPES_PAGE = "/couriers?command=all_cargo_types";
    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory = new ServiceFactory();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        Router router = new Router();
        try {
            router.setRoute(ROUT_TO_DISPLAY_ALL_TRANSPORT_TYPES_PAGE);
            router.setType(Router.Type.REDIRECT);
            String transportType =  request.getParameter(TRANSPORT_TYPE_ID_PARAMETER);
            service.delete(Integer.valueOf(transportType));
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(ROUT_TO_DISPLAY_ALL_TRANSPORT_TYPES_PAGE + "&error=" + e.getMessage());
        }
        return router;

    }
}
