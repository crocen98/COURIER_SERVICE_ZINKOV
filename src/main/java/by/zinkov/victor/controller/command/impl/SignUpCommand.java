package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.ServiceFactory;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String FIRST_NAME_PARAMETER = "first_name";
    private static final String LAST_NAME_PARAMETER = "last_name";
    private static final String EAMIL_PARAMETER = "email";
    private static final String PHONE_PARAMETER = "phone";
    private static final String ROLE_ID_PARAMETER = "role_id";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        ServiceFactory factory = new ServiceFactory();
        UserService service = factory.getUserService();
        User user = new User();


        try {
            service.signUp(user);
        } catch (ServiceException e) {
            throw new CommandException("msg", e);
        }
        return router;

    }
}
