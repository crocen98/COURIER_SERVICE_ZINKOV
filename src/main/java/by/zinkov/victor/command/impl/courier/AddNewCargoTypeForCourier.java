package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.CurrierCapability;
import by.zinkov.victor.domain.SupportedCargoTypes;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.*;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.AddNewCargoTypeForCourierValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AddNewCargoTypeForCourier implements Command {
    private static final Logger LOGGER = LogManager.getLogger(NewTransportTypeForCourier.class);
    private static final String CARGO_TYPE_ID_PARAMETER = "cargo_type_id";

    private static final String USER = "user";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrl());

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute(USER);
        Integer userId = userDto.getId();

        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        AddNewCargoTypeForCourierValidator validator = validatorFactory.getAddNewCargoTypeForCourierValidator();

        try {
            Map<String, String> errorMap = validator.validate(parameters);
            if (errorMap.size() != 0) {
                router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError(errorMap.get(CARGO_TYPE_ID_PARAMETER)));
                return router;
            }
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CurrierCapabilityService currierCapabilityService = serviceFactory.getCurrierCapabilityService();


            CurrierCapability capability = currierCapabilityService.getByCourierId(userId);

            if (capability == null) {
                router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError("error.unsupporedaction")); ///change
                return router;
            }

            CargoTypeService cargoTypeService = serviceFactory.getCargoTypeService();
            Integer idCargoType =  Integer.valueOf(parameters.get(CARGO_TYPE_ID_PARAMETER));
            CargoType cargoType = cargoTypeService.getById(idCargoType);

            SupportedCargoTypes supportedCargoTypes = new SupportedCargoTypes();
            supportedCargoTypes.setTypeId(cargoType.getId());
            supportedCargoTypes.setCurrierCapabilityId(capability.getId());

            SupportedCargoTypeService supportedCargoTypeService = serviceFactory.getSupportedCargoTypeService();
            supportedCargoTypeService.add(supportedCargoTypes);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.EDIT_COURIER_PROFILE_PAGE.getUrlWithError("error.error")); ///change
        }

        return router;
    }
}
