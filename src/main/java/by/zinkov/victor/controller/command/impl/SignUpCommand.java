package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.service.RegistrationKeyService;
import by.zinkov.victor.service.ServiceFactory;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.RegistrationKeyServiceImpl;
import by.zinkov.victor.util.MailSender;
import by.zinkov.victor.util.StringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String FIRST_NAME_PARAMETER = "first_name";
    private static final String LAST_NAME_PARAMETER = "last_name";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PHONE_PARAMETER = "phone";
    private static final String USER_ROLE_PARAMETER = "user_role";
    private static final String COORDINATES_COMMAND = "coordinates";

    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.ACTIVATE_PAGE.getRout());
        User user = new User();

        user.setUserStatus(UserStatus.WAITING_CONFIRMATION.getId());

        user.setFirstName(request.getParameter(FIRST_NAME_PARAMETER));
        user.setLastName(request.getParameter(LAST_NAME_PARAMETER));
        user.setPassword(request.getParameter(PASSWORD_PARAMETER));
        user.setLogin(request.getParameter(LOGIN_PARAMETER));
        user.setEmail(request.getParameter(EMAIL_PARAMETER));
        user.setPhone(request.getParameter(PHONE_PARAMETER));
        user.setLocation(request.getParameter(COORDINATES_COMMAND));
        String role = request.getParameter(USER_ROLE_PARAMETER);
        ServiceFactory factory = new ServiceFactory();
        UserService service = factory.getUserService();
        try {
            LOGGER.info(user + " with setted parameters before go to service.signUp(user, role)");
            user = service.signUp(user, role);
            StringGenerator generator = new StringGenerator();
            String randomString = generator.generate();
            MailSender sender = MailSender.getInstance();
            Integer port = request.getLocalPort();
            String url = request.getRemoteAddr() + ":" + port + request.getContextPath();
            String activateLink = registrationLinkBuild(randomString,user.getId(),url);
            LOGGER.info(activateLink);
            sender.sendEmail(activateLink,user.getEmail());
            RegistrationKeyService registrationKeyService = new RegistrationKeyServiceImpl();
            registrationKeyService.add(user.getId() ,randomString );

        } catch (ServiceException e) {
            throw new CommandException("msg", e);
        }
        return router;
    }

    private String registrationLinkBuild(String randomString ,Integer userId, String url){
        return String.format("Hi!  your link: %s/couriers?command=activate&user_id=%d&value=%s", url,userId,randomString);
    }
}
