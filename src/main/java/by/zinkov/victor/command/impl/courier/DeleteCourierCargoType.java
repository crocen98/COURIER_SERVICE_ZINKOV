package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.SupportedCargoTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.UtilValidator;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.DeleteByIdValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class DeleteCourierCargoType extends Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteCourierCargoType.class);
    private static final String USER = "user";
    private static final String CARGO_TYPE_ID_PARAMETER = "cargo_type_id";
    private static final String CARGO_TYPE_ID_ERROR_KEY = "cargo_type_id.validation.error";



    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrl());

        Map<String,String> parameters = super.readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        DeleteByIdValidator validator = validatorFactory.getDeleteByIdValidator();
        validator.setErrorKey(CARGO_TYPE_ID_ERROR_KEY);
        validator.setIdParameter(CARGO_TYPE_ID_PARAMETER);
        Map<String, String> errorsMap = validator.validate(parameters);
        String cargoTypeId = request.getParameter(CARGO_TYPE_ID_PARAMETER);

        if(errorsMap.size() != 0){
            initRouterForFaildValidation(router,request,errorsMap);

            return router;
        }

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SupportedCargoTypeService supportedCargoTypeService = serviceFactory.getSupportedCargoTypeService();
        try {
            supportedCargoTypeService.deleteByCourierId(userDto.getId(),Integer.valueOf(cargoTypeId));
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError(e.getErrorKey()));
        }
        return router;
    }
}
