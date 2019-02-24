package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.RegistrationKey;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.RegistrationKeyServiceImpl;
import by.zinkov.victor.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActivateCommand implements Command {
    private static final String USER_ID = "user_id";
    private static final String ACTIVATE_STRING = "value";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);

        Integer id = Integer.valueOf(request.getParameter(USER_ID));
        String value = request.getParameter(ACTIVATE_STRING);

        RegistrationKeyService service = new RegistrationKeyServiceImpl();
        try {
            RegistrationKey key = service.getById(id);
            if(key.getKey().equals(value)){
                UserService userService = new UserServiceImpl();
                UserDto userDto  = userService.getByPK(id);
                service.remove(key);
                HttpSession session = request.getSession();
                session.setAttribute("user", userDto);
                router.setRoute(Page.START_AUTHORIZED_PAGE.getRout());
            } else {
                throw new CommandException("Attempt to gain unauthorized access!");
            }
        } catch (ServiceException e) {
            router.setRoute(Page.START_PAGE.getRout());

        }
        return router;
    }
}
