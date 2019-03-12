package by.zinkov.victor.command.impl.user;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.exception.CommandException;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.CustomerReviewsService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.SetUserMarkValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class SetUserMark implements Command {
    private static final String ERRORS_ATTRIBUTE = "errors";

    private static final String RATING_PARAMETER = "rating";
    private static final String COURIER_ID_PARAMETER = "courier_id";
    private static final String USER = "user";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.TO_CLIENT_COURIERS_PAGE.getUrl());
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto)session.getAttribute(USER);
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        SetUserMarkValidator setUserMarkValidator = validatorFactory.getSetUserMarkValidator();
        Map<String,String> errors = setUserMarkValidator.validate(parameters);

        if (errors.size() != 0){
            router.setType(Router.Type.FORWARD);
            router.setRoute(Page.START_AUTHORIZED_PAGE.getRout());
            request.setAttribute(ERRORS_ATTRIBUTE,errors);
            return router;
        }


        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CustomerReviewsService customerReviewsService = serviceFactory.getCustomerReviewsService();
        Integer courierId =Integer.valueOf(parameters.get(COURIER_ID_PARAMETER));
        Integer rating =Integer.valueOf(parameters.get(RATING_PARAMETER));

        try {
            if(customerReviewsService.haveMark(courierId,userDto.getId())){
                customerReviewsService.updateCourierMark(courierId,userDto.getId(),rating);
            } else {
                customerReviewsService.setCourierMark(courierId,userDto.getId(),rating);
            }
        } catch (ServiceException e) {
            e.getMessage();
        }


        return router;
    }
}
