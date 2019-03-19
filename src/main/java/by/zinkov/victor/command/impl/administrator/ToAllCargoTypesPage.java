package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ToAllCargoTypesPage implements Command {
    private static final String LIST_CARGO_TYPES_ATTRIBUTE = "cargo_types";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        CargoTypeService service = factory.getCargoTypeService();
        try {
            Router router = new Router();
            router.setType(Router.Type.FORWARD);
            List<CargoType> cargoTypes = service.getAllCargoTypes();
            request.setAttribute(LIST_CARGO_TYPES_ATTRIBUTE, cargoTypes);
            router.setRoute(Page.ALL_CARGO_TYPES.getRout());
            return router;
        } catch (ServiceException e) {
            throw new CommandException("Exception in show all cargo types page", e);
        }
    }
}
