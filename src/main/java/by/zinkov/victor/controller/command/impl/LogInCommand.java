package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class);

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);

        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        UserService service = new UserServiceImpl();
        try {
            UserDto userDto = service.LogIn(login,password);
            HttpSession session = request.getSession();
            session.setAttribute(USER_ATTRIBUTE , userDto);
            router.setRoute(Router.INDEX_ROUT);
           if(userDto.getUserStatus() == UserStatus.BLOCKED){
               router.setRoute(Router.INDEX_ERROR_ROUT+ "BLOCKED");
               session.setAttribute(USER_ATTRIBUTE,null);
           } else if(userDto.getUserStatus() == UserStatus.WAITING_CONFIRMATION){
               router.setRoute(Router.INDEX_ERROR_ROUT+ "WAITING_CONFIRMATION");
               session.setAttribute(USER_ATTRIBUTE,null);
           }
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(Router.INDEX_ERROR_ROUT + " Service error");
        }
        return router;
    }
}
