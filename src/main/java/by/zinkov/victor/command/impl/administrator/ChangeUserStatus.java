package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.*;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.AddTransportTypeValidator;
import by.zinkov.victor.validation.impl.ChangeUserStatusValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ChangeUserStatus implements Command {
    private static final String USER_ID_PARAMETER = "user_id";
    private static final String ERRORS_ATTRIBUTE = "errors";
    private static final String USER_DTO_ATTRIBUTE = "user";
    private static final String YOU_CANNOT_CHANGE_YOUR_STATUS_ERROR = "users_table.adminchangeownstatus.error";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.TO_ALL_USERS_PAGE_COMMAND.getUrl());

        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ChangeUserStatusValidator addTransportTypeValidator = validatorFactory.getChangeUserStatusValidator();
        Map<String, String> errors = null;
        try {
            errors = addTransportTypeValidator.validate(parameters);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (errors.size() != 0){
            router.setType(Router.Type.FORWARD);
            router.setRoute(Page.INDEX.getRout());
            request.setAttribute(ERRORS_ATTRIBUTE,errors);
            return router;
        }

        Integer userId = Integer.valueOf(parameters.get(USER_ID_PARAMETER));

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER_DTO_ATTRIBUTE);
        if(userDto.getId().equals(userId)){
            router.setRoute(CommandEnum.TO_ALL_USERS_PAGE_COMMAND.getUrlWithError(YOU_CANNOT_CHANGE_YOUR_STATUS_ERROR));
            return router;
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            userService.changeStatus(userId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }


        return router;
    }
}
