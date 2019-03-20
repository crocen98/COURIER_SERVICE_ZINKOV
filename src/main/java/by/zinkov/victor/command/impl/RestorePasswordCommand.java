package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RestorePasswordCommand extends Command {
    private static final String USER_ID = "user_id";
    private static final String PASSWORD = "password";
    private static final String ACTIVATE_STRING = "key";
    private static final String USER_ATTRIBUTE = "user";
    private static final Logger LOGGER = LogManager.getLogger(RestorePasswordCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(Router.INDEX_ROUT);

        String id = request.getParameter(USER_ID);
        String password = request.getParameter(PASSWORD);
        String value = request.getParameter(ACTIVATE_STRING);

        UserService userService = new UserServiceImpl();
        try {
            userService.changePassword(id,password,value);
            UserDto userDto = userService.getByPK(Integer.valueOf(id));
            request.getSession().setAttribute(USER_ATTRIBUTE, userDto);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.TO_PASSWORD_RECOVERY_PAGE.getUrlWithError("Cannot recovery password!"));
        }
        return  router;
    }
}
