package by.zinkov.victor.command.impl.administrator;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class AddTransportTypeCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddTransportTypeCommand.class);
    private static final String TRANSPORT_NAME_PARAMETER = "transport_name";

    @Override
    public Router execute(HttpServletRequest request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        TransportTypeService service = factory.getTransportTypeServiceImpl();
        Router router = new Router();
        try {
            System.out.println(request.getCharacterEncoding());
            request.setCharacterEncoding("UTF-8");
            System.out.println(request.getCharacterEncoding());

            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrl());
            router.setType(Router.Type.REDIRECT);
            String transportName =  request.getParameter(TRANSPORT_NAME_PARAMETER);
            System.out.println(transportName);
            service.add(transportName);
        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(CommandEnum.ALL_TRANSPORT_TYPES.getUrlWithError(e.getMessage()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return router;
    }
}
