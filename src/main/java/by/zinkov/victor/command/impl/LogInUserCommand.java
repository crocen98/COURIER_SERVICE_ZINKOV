package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;

import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.exception.CommandException;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogInUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogInUserCommand.class);

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);

        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        UserService service = new UserServiceImpl();
        try {
            Thread.sleep(400);
            System.out.println("LOGIN");
            UserDto userDto = service.LogIn(login,password);
            System.out.println("LOGIN after");

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
            router.setRoute(CommandEnum.TO_LOG_IN_PAGE.getUrlWithError("login.error"));
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new CommandException("Problem with pause!",e);
        }
        return router;
    }
}
