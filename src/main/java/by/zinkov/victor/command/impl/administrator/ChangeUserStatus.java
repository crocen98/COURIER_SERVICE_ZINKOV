package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.*;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.ChangeUserStatusValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ChangeUserStatus extends Command {
    private static final String USER_ID_PARAMETER = "user_id";
    private static final String USER_DTO_ATTRIBUTE = "user";
    private static final String PAGE_PARAMETER = "page";
    private static final String YOU_CANNOT_CHANGE_YOUR_STATUS_ERROR = "users_table.adminchangeownstatus.error";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Map<String,String> parameters = readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ChangeUserStatusValidator addTransportTypeValidator = validatorFactory.getChangeUserStatusValidator();
        Map<String, String> errors;
        try {
            errors = addTransportTypeValidator.validate(parameters);
        if (errors.size() != 0){
            initRouterForFaildValidation(router,request,errors);
            return router;
        }

        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.TO_ALL_USERS_PAGE_COMMAND.getUrl() + "&page=" + parameters.get(PAGE_PARAMETER));
        Integer userId = Integer.valueOf(parameters.get(USER_ID_PARAMETER));

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER_DTO_ATTRIBUTE);
        if(userDto.getId().equals(userId)){
            router.setRoute(CommandEnum.TO_ALL_USERS_PAGE_COMMAND.getUrlWithError(YOU_CANNOT_CHANGE_YOUR_STATUS_ERROR));
            return router;
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

            userService.changeStatus(userId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }


        return router;
    }
}
