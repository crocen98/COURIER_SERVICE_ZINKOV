package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.domain.RegistrationKey;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.RegistrationKeyServiceImpl;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.validation.Validator;
import by.zinkov.victor.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ActivateCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(ActivateCommand.class);
    private static final String USER_ID = "user_id";
    private static final String ACTIVATE_STRING = "value";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ERRORS = "errors";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);

        Map<String,String> parameters = super.readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        Validator validator = validatorFactory.getActivateCommandValidator();
        RegistrationKeyService service = new RegistrationKeyServiceImpl();
        try {
            Map<String,String> errorsMap =  validator.validate(parameters);
            if(errorsMap.size() != 0){
                router.setRoute(Page.INDEX.getRout());
                router.setType(Router.Type.FORWARD);
                request.setAttribute(ERRORS,errorsMap);
                return router;
            }

            Integer id = Integer.valueOf(parameters.get(USER_ID));
            String value = parameters.get(ACTIVATE_STRING);

            RegistrationKey key = service.getById(id);
            if(key.getKey().equals(value)){
                UserService userService = new UserServiceImpl();
                UserDto userDto  = userService.getByPK(id);
                userService.setNewStatus(userDto.getId(),UserStatus.ACTIVE.toString());
                userDto.setUserStatus(UserStatus.ACTIVE);
                service.remove(key);
                HttpSession session = request.getSession();
                session.setAttribute(USER_ATTRIBUTE, userDto);
                router.setRoute(Router.INDEX_ROUT);
            } else {
                throw new CommandException("Attempt to gain unauthorized access!");
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(Router.INDEX_ROUT);
        }
        return router;
    }
}
