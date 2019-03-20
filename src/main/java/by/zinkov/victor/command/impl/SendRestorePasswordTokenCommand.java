package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.SendRestorePasswordTokenValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SendRestorePasswordTokenCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(LogInUserCommand.class);

    private static final String LOGIN_PARAMETER = "login";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PHONE_PARAMETER = "phone";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRoute(Page.ACTIVATE_PAGE.getRout());
        router.setType(Router.Type.FORWARD);

        Map<String,String> parameters = readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        SendRestorePasswordTokenValidator validator = validatorFactory.getSendRestorePasswordTokenValidator();
        Map<String, String> errorsMap = validator.validate(parameters);

        if(errorsMap.size() == 0){
            LOGGER.warn("Not valid parameters enter user for restoring password");
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.TO_CHANGE_PASSWORD_PAGE.getUrlWithError("problem with changing password!"));
        }
        try {

            User user = new User();
            user.setLogin(parameters.get(LOGIN_PARAMETER));
            user.setEmail(parameters.get(EMAIL_PARAMETER));
            user.setPhone(parameters.get(PHONE_PARAMETER));

            UserService service = new UserServiceImpl();
            service.restoreUserByEmail(user,request);

        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.TO_CHANGE_PASSWORD_PAGE.getUrlWithError(e.getErrorKey()));
        }
        return router;
    }
}
