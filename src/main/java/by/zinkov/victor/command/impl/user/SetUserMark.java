package by.zinkov.victor.command.impl.user;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.CommandException;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.CustomerReviewsService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.SetUserMarkValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;

public class SetUserMark extends Command {

    private static final String RATING_PARAMETER = "rating";
    private static final String COURIER_ID_PARAMETER = "courier_id";
    private static final String USER = "user";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.TO_CLIENT_COURIERS_PAGE.getUrl());
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);
        Map<String, String> parameters = readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        SetUserMarkValidator setUserMarkValidator = validatorFactory.getSetUserMarkValidator();
        Map<String, String> errors = setUserMarkValidator.validate(parameters);

        if (errors.size() != 0) {
            initRouterForFaildValidation(router, request, errors);

            return router;
        }


        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CustomerReviewsService customerReviewsService = serviceFactory.getCustomerReviewsService();
        Integer courierId = Integer.valueOf(parameters.get(COURIER_ID_PARAMETER));
        Integer rating = Integer.valueOf(parameters.get(RATING_PARAMETER));

        try {
            customerReviewsService.setCourierMark(courierId, userDto.getId(), rating);
        } catch (ServiceException e) {
            throw new CommandException("error.error", e);
        }
        return router;
    }
}
