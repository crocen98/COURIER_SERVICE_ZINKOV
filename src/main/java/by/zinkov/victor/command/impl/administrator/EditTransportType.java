package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.builder.BuilderFactory;
import by.zinkov.victor.builder.impl.TransportTypeBuilder;
import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.AddTransportTypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class EditTransportType implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditTransportType.class);
    private static final String ERRORS_ATTRIBUTE = "error";

    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory =  ServiceFactory.getInstance();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        Router router = new Router();



        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        AddTransportTypeValidator addTransportTypeValidator = validatorFactory.getAddTransportTypeValidator();
        Map<String, String> errors = addTransportTypeValidator.validate(parameters);
        if (errors.size() != 0){
            router.setType(Router.Type.FORWARD);
            router.setRoute(Page.INDEX.getRout());
            request.setAttribute(ERRORS_ATTRIBUTE,errors);
            return router;
        }

        BuilderFactory builderFactory = BuilderFactory.getInstance();
        TransportTypeBuilder builder = builderFactory.getTransportTypeBuilder();
        TransportType transportType = builder.build(parameters);




        try {
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrl());
            router.setType(Router.Type.REDIRECT);
            service.edit(transportType);

        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrlWithError(e.getMessage()));
        }
        return router;
    }
}
