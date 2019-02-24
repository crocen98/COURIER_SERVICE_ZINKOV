package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService service = new UserServiceImpl();
        try {
            UserDto userDto = service.LogIn(login,password);
            HttpSession session = request.getSession();
            session.setAttribute("user" , userDto);
            router.setRoute(Page.START_AUTHORIZED_PAGE.getRout());
        } catch (ServiceException e) {
            router.setRoute(CommandEnum.TO_LOG_IN_PAGE.getUrlWithError(e.getMessage()));
            router.setType(Router.Type.REDIRECT);
        }
        return router;
    }
}
