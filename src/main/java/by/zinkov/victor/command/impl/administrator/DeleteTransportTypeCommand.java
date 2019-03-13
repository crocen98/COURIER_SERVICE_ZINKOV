package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteTransportTypeCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTransportTypeCommand.class);

    private static final String TRANSPORT_TYPE_ID_PARAMETER = "transport_type_id";
    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory =  ServiceFactory.getInstance();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        Router router = new Router();
        try {
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrl());
            router.setType(Router.Type.REDIRECT);
            String transportType =  request.getParameter(TRANSPORT_TYPE_ID_PARAMETER);
            service.delete(Integer.valueOf(transportType));
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrlWithError(e.getMessage()));
        }
        return router;

    }
}