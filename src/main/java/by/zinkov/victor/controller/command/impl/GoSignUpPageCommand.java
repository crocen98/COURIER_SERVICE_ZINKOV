package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.service.UserRoleService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.UserRoleServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoSignUpPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.SIGN_UP.getRout());

        UserRoleService userRoleService = new UserRoleServiceImpl();
        try {
            List<UserRole> userRoles = userRoleService.getAll();
            userRoles.remove(UserRole.ADMINISTRATOR);
            request.setAttribute("user_roles" , userRoles);
        } catch (ServiceException e) {
            throw new CommandException("Problem get all users roles",e);
        }
        return router;
    }
}
