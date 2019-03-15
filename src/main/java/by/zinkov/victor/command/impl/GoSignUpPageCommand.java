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

public class GoSignUpPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.SIGN_UP.getRout());


        /// for test
//        Map<String,String> errors = new HashMap();
//        errors.put("err1","login.error");
//        errors.put("err2","login.error");
//
//        request.setAttribute("errors", errors);
//




        //// end test

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
