package by.zinkov.victor.controller.command.impl.administrator;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.service.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditTransportType implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditTransportType.class);
    private static final String TRANSPORT_NAME_PARAMETER = "transport_name";
    private static final String TRANSPORT_ID_PARAMETER = "transport_type_id";

    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory = new ServiceFactory();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        Router router = new Router();
        try {
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrl());
            router.setType(Router.Type.REDIRECT);
            String transportName = request.getParameter(TRANSPORT_NAME_PARAMETER);
            String transportId = request.getParameter(TRANSPORT_ID_PARAMETER);
            service.edit(transportId, transportName);

        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrlWithError(e.getMessage()));
        }
        return router;
    }
}
