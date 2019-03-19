package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteCargoType implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTransportTypeCommand.class);

    private static final String CARGO_TYPE_ID_PARAMETER = "cargo_type_id";
    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory =  ServiceFactory.getInstance();
        CargoTypeService service = factory.getCargoTypeService();
        Router router = new Router();
        try {
            router.setRoute(CommandEnum.ALL_CARGO_TYPES.getUrl());
            router.setType(Router.Type.REDIRECT);
            String cargoTypeId =  request.getParameter(CARGO_TYPE_ID_PARAMETER);
            service.deleteById(Integer.valueOf(cargoTypeId));
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_CARGO_TYPES.getUrlWithError(e.getMessage()));
        }
        return router;

    }
}
