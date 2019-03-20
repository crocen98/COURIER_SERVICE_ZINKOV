package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.service.UserRoleService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.UserRoleServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoSignUpPageCommand extends Command {

    private static final String USER_ROLES_ATTRIBUTE = "user_roles";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.SIGN_UP.getRout());
        UserRoleService userRoleService = new UserRoleServiceImpl();
        try {
            List<UserRole> userRoles = userRoleService.getAll();
            userRoles.remove(UserRole.ADMINISTRATOR);
            request.setAttribute(USER_ROLES_ATTRIBUTE , userRoles);
        } catch (ServiceException e) {
            throw new CommandException("Problem get all users roles",e);
        }
        return router;
    }
}
