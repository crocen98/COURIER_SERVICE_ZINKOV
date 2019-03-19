package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.builder.BuilderFactory;
import by.zinkov.victor.builder.impl.CargoTypeBuilder;
import by.zinkov.victor.command.*;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.AddCargoTypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AddCargoType implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddCargoType.class);
    private static final String ERRORS_ATTRIBUTE = "error";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setRoute(CommandEnum.ALL_CARGO_TYPES.getUrl());
        router.setType(Router.Type.REDIRECT);

        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        AddCargoTypeValidator addCargoTypeValidator = validatorFactory.getAddCargoTypeValidator();
        Map<String, String> errors = addCargoTypeValidator.validate(parameters);
        if (errors.size() != 0){
            router.setType(Router.Type.FORWARD);
            router.setRoute(Page.INDEX.getRout());
            request.setAttribute(ERRORS_ATTRIBUTE,errors);
            return router;
        }

        BuilderFactory builderFactory = BuilderFactory.getInstance();
        CargoTypeBuilder builder = builderFactory.getCargoTypeBuilder();
        CargoType cargoType = builder.build(parameters);
        ServiceFactory factory = ServiceFactory.getInstance();
        CargoTypeService service = factory.getCargoTypeService();
        try {
            service.add(cargoType);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_CARGO_TYPES.getUrlWithError(e.getMessage()));
        }
        return router;
    }
}
