package by.zinkov.victor.command.impl;

import by.zinkov.victor.builder.BuilderFactory;
import by.zinkov.victor.builder.impl.UserBuilder;
import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.RegistrationKeyServiceImpl;
import by.zinkov.victor.util.MailSender;
import by.zinkov.victor.util.StringGenerator;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.SignUpValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
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
            user = service.signUp(user);
            sendActivateEmail(user, request);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.SIGN_UP.getUrlWithError(e.getErrorKey()));
        }
        return router;
    }

    private String registrationLinkBuild(String randomString, Integer userId, String url) {
        return String.format("Hi!  your link: %s/index?command=activate&user_id=%d&value=%s", url, userId, randomString);
    }

    private void sendActivateEmail(User user, HttpServletRequest request) throws ServiceException {
        StringGenerator generator = new StringGenerator();
        String randomString = generator.generate();
        MailSender sender = MailSender.getInstance();
        Integer port = request.getLocalPort();
        String url;
        url = "http://127.0.0.1" + ":" + port + request.getContextPath();
        String activateLink = registrationLinkBuild(randomString, user.getId(), url);
        LOGGER.info(activateLink);
        sender.sendEmail(activateLink, user.getEmail());
        RegistrationKeyService registrationKeyService = new RegistrationKeyServiceImpl();
        registrationKeyService.add(user.getId(), randomString);
    }
}
