package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.DeleteByIdValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DeleteTransportTypeCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTransportTypeCommand.class);
    private static final String ERROR = "error";

    private static final String TRANSPORT_TYPE_ID_PARAMETER = "transport_type_id";
    private static final String TRANSPORT_TYPE_ID_ERROR_KEY = "transport_type_id.validation.error";


    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory =  ServiceFactory.getInstance();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        Router router = new Router();

        Map<String, String> parameters = super.readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        DeleteByIdValidator validator = validatorFactory.getDeleteByIdValidator();
        validator.setErrorKey(TRANSPORT_TYPE_ID_ERROR_KEY);
        validator.setIdParameter(TRANSPORT_TYPE_ID_PARAMETER);
        Map<String, String> errorsMap = validator.validate(parameters);
        if (errorsMap.size() != 0) {
            request.setAttribute(ERROR, errorsMap);
            router.setRoute(Router.INDEX_ROUT);
            router.setType(Router.Type.FORWARD);
        }

        try {
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrl());
            router.setType(Router.Type.REDIRECT);
            String transportType =  parameters.get(TRANSPORT_TYPE_ID_PARAMETER);
            service.delete(Integer.valueOf(transportType));
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrlWithError(e.getMessage()));
        }
        return router;

    }
}
