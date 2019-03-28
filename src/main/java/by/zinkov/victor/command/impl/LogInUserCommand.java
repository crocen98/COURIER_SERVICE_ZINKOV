package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.*;

import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.LogInUserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class LogInUserCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(LogInUserCommand.class);

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_HASH_PARAMETER = "password_hash";
    private static final String USER_ATTRIBUTE = "user";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);


        Map<String, String> parameters = super.readParameters(request);

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        LogInUserValidator validator = validatorFactory.getLogInUserValidator();
        Map<String, String> errorsMap = validator.validate(parameters);

        if(errorsMap.size() != 0){
            initRouterForFaildValidation(router,request,errorsMap);
            return router;
        }

        String login = parameters.get(LOGIN_PARAMETER);
        String password = parameters.get(PASSWORD_HASH_PARAMETER);

        UserService service = new UserServiceImpl();
        try {
            Thread.sleep(200);
            UserDto userDto = service.LogIn(login, password);

            if (userDto.getUserStatus() == UserStatus.BLOCKED) {
                router.setRoute(Router.INDEX_ERROR_ROUT + "BLOCKED");
            } else if (userDto.getUserStatus() == UserStatus.WAITING_CONFIRMATION) {
                router.setRoute(Router.INDEX_ERROR_ROUT + "WAITING_CONFIRMATION");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(USER_ATTRIBUTE, userDto);
                router.setRoute(Router.INDEX_ROUT);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.TO_LOG_IN_PAGE.getUrlWithError(e.getErrorKey()));
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new CommandException("Problem with pause!", e);
        }
        return router;
    }
}
