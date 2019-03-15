package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.SupportedCargoTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteCourierCargoType implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteCourierCargoType.class);
    private static final String USER = "user";
    private static final String CARGO_TYPE_ID_PARAMETER = "cargo_type_id";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrl());

        String cargoTypeId = request.getParameter(CARGO_TYPE_ID_PARAMETER);
        UtilValidator validator = UtilValidator.getInstance();
        if (!validator.isMatchesInt(cargoTypeId, UtilValidator.POSITIVE_RANGE)) {
            router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError("validation.notvalidint.error"));
            return router;
        }

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SupportedCargoTypeService supportedCargoTypeService = serviceFactory.getSupportedCargoTypeService();
        try {
            supportedCargoTypeService.deleteByCourierId(userDto.getId(),Integer.valueOf(cargoTypeId));
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError("cargotype.cannotdelete.error"));
        }
        return router;
    }
}
