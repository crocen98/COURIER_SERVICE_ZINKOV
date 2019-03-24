package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.RestorePasswordValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RestorePasswordCommand extends Command {
    private static final String USER_ID = "user_id";
    private static final String PASSWORD = "password_hash";
    private static final String ACTIVATE_STRING = "key";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ERRORS = "errors";
    private static final Logger LOGGER = LogManager.getLogger(RestorePasswordCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(Router.INDEX_ROUT);


        Map<String,String> parameters = super.readParameters(request);

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        RestorePasswordValidator validator =  validatorFactory.getRestorePasswordValidator();

        Map<String,String> errorsMap =  validator.validate(parameters);
        if(errorsMap.size() != 0){
            router.setRoute(Page.INDEX.getRout());
            router.setType(Router.Type.FORWARD);
            request.setAttribute(ERRORS,errorsMap);
            return router;
        }

        String id = parameters.get(USER_ID);
        String password = parameters.get(PASSWORD);
        String value = parameters.get(ACTIVATE_STRING);

        UserService userService = new UserServiceImpl();
        try {
            userService.changePassword(id,password,value);
            UserDto userDto = userService.getByPK(Integer.valueOf(id));
            request.getSession().setAttribute(USER_ATTRIBUTE, userDto);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.TO_PASSWORD_RECOVERY_PAGE.getUrlWithError(e.getErrorKey()));
        }
        return  router;
    }
}
