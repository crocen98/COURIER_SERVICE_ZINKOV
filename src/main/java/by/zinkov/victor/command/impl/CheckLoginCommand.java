package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.validation.UtilValidator;

import javax.servlet.http.HttpServletRequest;

public class CheckLoginCommand extends Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String LOGIN_STATUS_ATTRIBUTE = "login_status";
    private static final int MAX_FIELD_LENGTH = 35;
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN_PARAMETER);
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.LABEL_FOR_ERROR_INPUT.getRout());

        UserService service = new UserServiceImpl();
        UtilValidator validator = UtilValidator.getInstance();
        try {
            if(!validator.simpleStingMatches(login, MAX_FIELD_LENGTH)){
                request.setAttribute(LOGIN_STATUS_ATTRIBUTE, true);
            }
            User user = service.getByLogin(login);
            request.setAttribute(LOGIN_STATUS_ATTRIBUTE, user == null ? true : false);
            return router;
        } catch (ServiceException e) {
            throw new CommandException("Problem with checking user", e);
        }
    }
}
