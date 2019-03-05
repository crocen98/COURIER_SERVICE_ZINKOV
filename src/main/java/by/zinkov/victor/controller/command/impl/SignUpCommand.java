package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.RegistrationKeyServiceImpl;
import by.zinkov.victor.util.MailSender;
import by.zinkov.victor.util.RequestEntityBuilder;
import by.zinkov.victor.util.StringGenerator;
import by.zinkov.victor.util.excepton.EntityFromRequestBuilderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

    private static final String USER_ROLE_PARAMETER = "user_role";
    private static final String LOGIN_FIELD = "login";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String PASSWORD_FIELD = "password";
    private static final String LOCATION_FIELD = "location";


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.ACTIVATE_PAGE.getRout());
        String role = request.getParameter(USER_ROLE_PARAMETER);

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService service = factory.getUserService();
        try {
            RequestEntityBuilder builder = new RequestEntityBuilder();
            User user = builder.build(request, User.class, LOGIN_FIELD, FIRST_NAME_FIELD, LAST_NAME_FIELD, EMAIL_FIELD, PHONE_FIELD, PASSWORD_FIELD, LOCATION_FIELD);

            user = service.signUp(user, role);
            sendActivateEmail(user, request);
        } catch (ServiceException | EntityFromRequestBuilderException e) {
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.SIGN_UP.getUrlWithError("problem with creating account!"));
        }

        return router;
    }

    private String registrationLinkBuild(String randomString, Integer userId, String url) {
        return String.format("Hi!  your link: %s/couriers?command=activate&user_id=%d&value=%s", url, userId, randomString);
    }

    private void sendActivateEmail(User user, HttpServletRequest request) throws ServiceException {
        StringGenerator generator = new StringGenerator();
        String randomString = generator.generate();
        MailSender sender = MailSender.getInstance();
        Integer port = request.getLocalPort();
        String url;
        url = "http://207.154.220.222" + ":" + port + request.getContextPath();
        String activateLink = registrationLinkBuild(randomString, user.getId(), url);
        LOGGER.info(activateLink);
        sender.sendEmail(activateLink, user.getEmail());
        RegistrationKeyService registrationKeyService = new RegistrationKeyServiceImpl();
        registrationKeyService.add(user.getId(), randomString);
    }
}
