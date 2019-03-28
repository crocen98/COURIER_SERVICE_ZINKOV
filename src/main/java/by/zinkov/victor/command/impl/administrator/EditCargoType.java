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
import java.util.Map;

public class EditCargoType extends Command {
    private static final Logger LOGGER = LogManager.getLogger(EditCargoType.class);

    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory =  ServiceFactory.getInstance();
        CargoTypeService service = factory.getCargoTypeService();
        Router router = new Router();

        Map<String,String> parameters = readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        AddCargoTypeValidator addCargoTypeValidator = validatorFactory.getAddCargoTypeValidator();
        Map<String, String> errors = addCargoTypeValidator.validate(parameters);
        if (errors.size() != 0){
            initRouterForFaildValidation(router,request,errors);
            return router;
        }

        BuilderFactory builderFactory = BuilderFactory.getInstance();
        CargoTypeBuilder builder = builderFactory.getCargoTypeBuilder();
        CargoType cargoType = builder.build(parameters);

        try {
            router.setRoute(CommandEnum.ALL_CARGO_TYPES.getUrl());
            router.setType(Router.Type.REDIRECT);
            service.update(cargoType);

        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrlWithError(e.getErrorKey()));
        }
        return router;
    }
}
