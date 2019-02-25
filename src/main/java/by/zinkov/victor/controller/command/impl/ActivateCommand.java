package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.RegistrationKey;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.RegistrationKeyServiceImpl;
import by.zinkov.victor.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActivateCommand implements Command {
    private static final String USER_ID = "user_id";
    private static final String ACTIVATE_STRING = "value";
    private static final String USER_ATTRIBUTE = "user";
    private static final Logger LOGGER = LogManager.getLogger(ActivateCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);

        Integer id = Integer.valueOf(request.getParameter(USER_ID));
        String value = request.getParameter(ACTIVATE_STRING);

        RegistrationKeyService service = new RegistrationKeyServiceImpl();
        try {
            RegistrationKey key = service.getById(id);
            if(key.getKey().equals(value)){
                UserService userService = new UserServiceImpl();
                UserDto userDto  = userService.getByPK(id);
                userService.setNewStatus(userDto.getId(),"ACTIVE");
                userDto.setUserStatus(UserStatus.ACTIVE);


                service.remove(key);
                HttpSession session = request.getSession();
                session.setAttribute(USER_ATTRIBUTE, userDto);
                router.setRoute(Router.INDEX_ROUT);
            } else {
                throw new CommandException("Attempt to gain unauthorized access!");
            }
        } catch (ServiceException e) {
            //
            LOGGER.error(e);
            router.setRoute(Router.INDEX_ROUT);

        }
        return router;
    }
}
