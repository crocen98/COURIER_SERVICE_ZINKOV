package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.exception.ValidationException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.service.validation.StringValidator;

import javax.servlet.http.HttpServletRequest;

public class CheckLoginCommand implements Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String LOGIN_STATUS_ATTRIBUTE = "login_status";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN_PARAMETER);
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.LABEL_FOR_ERROR_INPUT.getRout());

        UserService service = new UserServiceImpl();
        StringValidator validator = StringValidator.getInstance();
        try {
            validator.simpleStingMatches(login, 45, "login");
            User user = service.getByLogin(login);
            if (user == null) {
                request.setAttribute(LOGIN_STATUS_ATTRIBUTE, true);
            } else {
                request.setAttribute(LOGIN_STATUS_ATTRIBUTE, false);
            }
            return router;
        } catch (ValidationException e) {
            throw new CommandException("Not valid data", e);
        } catch (ServiceException e) {
            throw new CommandException("Problem with checking user", e);
        }
    }
}
