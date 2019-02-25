package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.util.RequestEntityBuilder;
import by.zinkov.victor.util.excepton.EntityFromRequestBuilderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SendRestorePasswordTokenCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogInUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRoute(Page.ACTIVATE_PAGE.getRout());
        router.setType(Router.Type.FORWARD);
        RequestEntityBuilder builder = new RequestEntityBuilder();
        try {
            User user = builder.build(request, User.class);
            UserService service = new UserServiceImpl();
            service.restoreUserByEmail(user,request);
        } catch (ServiceException | EntityFromRequestBuilderException e) {
            LOGGER.error(e);
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.TO_CHANGE_PASSWORD_PAGE.getUrlWithError("problem with changing password!"));
        }
        return router;
    }
}
