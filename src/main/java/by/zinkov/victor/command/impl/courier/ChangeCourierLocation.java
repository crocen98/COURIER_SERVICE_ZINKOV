package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.domain.User;
import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeCourierLocation extends Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeCourierLocation.class);
    private static final String LOCATION_PARAMETER = "location";

    private static final String USER = "user";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrl());

        String location = request.getParameter(LOCATION_PARAMETER);
        UtilValidator validator = UtilValidator.getInstance();

        if (!validator.coordinatesMatches(location)) {
            router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError("validation.point.error"));
            return router;
        }

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);
        userDto.setLocation(location);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            User user = userService.getByLogin(userDto.getLogin());
            user.setLocation(location);
            userService.update(user);
            userDto.setLocation(location);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError(e.getErrorKey()));
        }
        return router;
    }
}
