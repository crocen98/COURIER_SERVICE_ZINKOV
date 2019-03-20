package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.CurrierCapability;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.CurrierCapabilityService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.NewTransportTypeForCourierValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class NewTransportTypeForCourier extends Command {
    private static final Logger LOGGER = LogManager.getLogger(NewTransportTypeForCourier.class);
    private static final String TRANSPORT_TYPE_ID_PARAMETER = "transport_type_id";

    private static final String USER = "user";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrl());

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);
        Integer userId = userDto.getId();

        Map<String,String> parameters = readParameters(request);

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        NewTransportTypeForCourierValidator validator = validatorFactory.getNewTransportTypeForCourierValidator();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CurrierCapabilityService currierCapabilityService = serviceFactory.getCurrierCapabilityService();

        try {

            Map<String, String> errorMap = validator.validate(parameters);
            if (errorMap.size() != 0) {
                router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError(errorMap.get(TRANSPORT_TYPE_ID_PARAMETER)));
                return router;
            }


            TransportTypeService transportTypeService = serviceFactory.getTransportTypeService();
            TransportType transportType = transportTypeService.getById(Integer.valueOf(parameters.get(TRANSPORT_TYPE_ID_PARAMETER)));
            CurrierCapability capability = currierCapabilityService.getByCourierId(userId);

            if (capability == null) {
                capability = new CurrierCapability();
                capability.setCurrierId(userId);
                capability.setTransportId(transportType.getId());
                currierCapabilityService.create(capability);
            }

            capability.setTransportId(transportType.getId());
            currierCapabilityService.update(capability);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError("error.error")); ///change
        }

        return router;
    }
}
