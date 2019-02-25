package by.zinkov.victor.controller.command.impl.administrator;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowTranspotTypesCommand implements Command {
    private static final String LIST_TRANSPORT_TYPES_ATTRIBUTE = "transport_types";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = new ServiceFactory();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        try {
            Router router = new Router();
            router.setType(Router.Type.FORWARD);
            List<TransportType> transportTypes = service.getAllTransportTypes();
            request.setAttribute(LIST_TRANSPORT_TYPES_ATTRIBUTE, transportTypes);
            router.setRoute(Page.ALL_TRANSPORT_TYPES.getRout());
            return router;
        } catch (ServiceException e) {
            throw new CommandException("Exception in show transportTypeCommand" , e);
        }
    }
}
