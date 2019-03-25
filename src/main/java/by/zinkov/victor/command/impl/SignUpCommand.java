package by.zinkov.victor.command.impl;

import by.zinkov.victor.builder.BuilderFactory;
import by.zinkov.victor.builder.impl.UserBuilder;
import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.SignUpValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SignUpCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

    private static final String ERRORS_ATTRIBUTE = "errors";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.ACTIVATE_PAGE.getRout());

        Map<String,String> parameters = readParameters(request);

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        SignUpValidator signUpValidator = validatorFactory.getSignUpValidator();
        Map<String, String> errors = signUpValidator.validate(parameters);
        if (errors.size() != 0){
            router.setType(Router.Type.FORWARD);
            router.setRoute(Page.INDEX.getRout());
            request.setAttribute(ERRORS_ATTRIBUTE,errors);
            return router;
        }
        BuilderFactory builderFactory = BuilderFactory.getInstance();
        UserBuilder userBuilder = builderFactory.getUserBuilder();
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService service = factory.getUserService();
        try {
            User user = userBuilder.build(parameters);
            service.signUp(user,request.getRequestURL().toString());
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.SIGN_UP.getUrlWithError(e.getErrorKey()));
        }
        return router;
    }

}
