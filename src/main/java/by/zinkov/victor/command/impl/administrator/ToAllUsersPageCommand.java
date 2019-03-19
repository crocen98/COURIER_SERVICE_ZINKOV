package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ToAllUsersPageCommand implements Command {
    private static final String USERS_ATTRIBUTE = "users";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.USERS_TABLE.getRout());

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        List<UserDto> users;
        try {
            users = userService.getAllUsersDto();
        } catch (ServiceException e) {
            throw new CommandException("Command ex", e);
        }
        request.setAttribute(USERS_ATTRIBUTE, users);
        return router;
    }
}
