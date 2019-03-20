package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.DeleteByIdValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DeleteCargoType extends Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteCargoType.class);

    private static final String CARGO_TYPE_ID_PARAMETER = "cargo_type_id";
    private static final String CARGO_TYPE_ID_ERROR_KEY = "cargo_type_id.validation.error";
    private static final String ERRORS = "errors";


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRoute(CommandEnum.ALL_CARGO_TYPES.getUrl());
        router.setType(Router.Type.REDIRECT);

        ServiceFactory factory = ServiceFactory.getInstance();
        CargoTypeService service = factory.getCargoTypeService();
        Map<String, String> parameters = super.readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        DeleteByIdValidator validator = validatorFactory.getDeleteByIdValidator();
        validator.setErrorKey(CARGO_TYPE_ID_ERROR_KEY);
        validator.setIdParameter(CARGO_TYPE_ID_PARAMETER);
        Map<String, String> errorsMap = validator.validate(parameters);
        if (errorsMap.size() != 0) {
            request.setAttribute(ERRORS, errorsMap);
            router.setRoute(Router.INDEX_ROUT);
            router.setType(Router.Type.FORWARD);
        }
        try {
            String cargoTypeId = parameters.get(CARGO_TYPE_ID_PARAMETER);
            service.deleteById(Integer.valueOf(cargoTypeId));
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_CARGO_TYPES.getUrlWithError(e.getErrorKey()));
        }
        return router;

    }
}
