package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.ToAllUsersPageValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public class ToAllUsersPageCommand extends Command {
    private static final String USERS_ATTRIBUTE = "users";
    private static final String PAGE_PARAMETER = "page";

    private static final String USERS_COUNT = "users_count";
    private static final String ERRORS = "errors";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.USERS_TABLE.getRout());
        Map<String, String> parameters = super.readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ToAllUsersPageValidator validator = validatorFactory.getToAllUsersPageValidator();
        Map<String, String> errorsMap = validator.validate(parameters);
        if (errorsMap.size() != 0) {
            request.setAttribute(ERRORS, errorsMap);
            router.setRoute(Router.INDEX_ROUT);
            router.setType(Router.Type.FORWARD);
            return router;
        }


        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        List<UserDto> users;
        try {
            int usersCount = userService.getUsersCount();
            request.setAttribute(USERS_COUNT,usersCount);
            int page = Integer.valueOf(parameters.get(PAGE_PARAMETER));
            users = userService.getAllUsersDto((page-1)*10);
        } catch (ServiceException e) {
            throw new CommandException("Command ex", e);
        }
        request.setAttribute(USERS_ATTRIBUTE, users);
        return router;
    }
}
