package by.zinkov.victor.controller.command.impl.user;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.*;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.impl.CargoTypeServiceImpl;
import by.zinkov.victor.service.impl.OrderServiceImpl;
import by.zinkov.victor.service.impl.TransportTypeServiceImpl;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.service.validation.StringValidator;
import by.zinkov.victor.util.RequestEntityBuilder;
import by.zinkov.victor.util.excepton.EntityFromRequestBuilderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.zinkov.victor.domain.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class CreateOrderSecondStage implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CreateOrderSecondStage.class);
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CARGO_TYPE_PARAMETER_ATTRIBUTE = "cargo_type";
    private static final String TRANSPORT_TYPE_PARAMETER_ATTRIBUTE = "transport_type";
    private static final String COURIERS_ATTRIBUTE = "couriers";
    private static final String START_POINT_FIELD = "startPoint";
    private static final String FINISH_POINT_FIELD = "finishPoint";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String START_TIME_FIELD = "startTime";
    private static final String DISTANCE_ATTRIBUTE = "distance";


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.CREATE_ORDER_SECOND_STAGE.getRout());

        HttpSession session = request.getSession();
        session.setAttribute(ORDER_ATTRIBUTE,null);

        String transportTypeString = request.getParameter(TRANSPORT_TYPE_PARAMETER_ATTRIBUTE);
        String cargoTypeString = request.getParameter(CARGO_TYPE_PARAMETER_ATTRIBUTE);
        request.setAttribute(TRANSPORT_TYPE_PARAMETER_ATTRIBUTE, transportTypeString);
        request.setAttribute(CARGO_TYPE_PARAMETER_ATTRIBUTE, cargoTypeString);

        ServiceFactory factory = ServiceFactory.getInstance();
        OrderStatusService orderStatusService = factory.getOrderStatusService();
        UserDto user = (UserDto)session.getAttribute(USER_ATTRIBUTE);


        try {
            if (orderStatusService.haveActiveOrder(user.getId())){
                router.setType(Router.Type.REDIRECT);
                router.setRoute(CommandEnum.TO_USER_ORDER_PAGE.getUrlWithError("orderpage.error"));
                System.out.println("i am hear");
                return router;
            }
            RequestEntityBuilder builder = new RequestEntityBuilder();
            Order order =  builder.build(request, Order.class, START_POINT_FIELD, FINISH_POINT_FIELD, START_TIME_FIELD,DESCRIPTION_FIELD);
            order.setIdCustomer(user.getId());
            UserService userService = new UserServiceImpl();
            List<User> couriers = userService.getCouriersByParams(transportTypeString, cargoTypeString);
            request.setAttribute(COURIERS_ATTRIBUTE, couriers);

            setOrderFields(order,transportTypeString,cargoTypeString);
            session.setAttribute(ORDER_ATTRIBUTE, order);

            double distance = calculateDistance(order);
            request.setAttribute(DISTANCE_ATTRIBUTE,distance);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(ORDER_ATTRIBUTE, null);
            router.setType(Router.Type.REDIRECT);

            router.setRoute(CommandEnum.TO_CREATE_ORDER_PAGE.getUrlWithError("Cannot create order! Problem in service"));
            e.printStackTrace();
        } catch (EntityFromRequestBuilderException e) {
            LOGGER.error(e);
            router.setType(Router.Type.REDIRECT);

            session.setAttribute(ORDER_ATTRIBUTE, null);
            router.setRoute(CommandEnum.TO_CREATE_ORDER_PAGE.getUrlWithError("Cannot create order! Problem in builder"));
        }
        return router;
    }

    private double calculateDistance(Order order){
        DistanceService service = new DistanceService();
        return service.calculate(order.getStartPoint(),order.getFinishPoint());
    }


    private void setOrderFields(Order order , String transportTypeString, String cargoTypeString) throws ServiceException {
        CargoTypeService cargoTypeService = new CargoTypeServiceImpl();
        CargoType cargoType = cargoTypeService.getByName(cargoTypeString);
        order.setIdCargoType(cargoType.getId());
        TransportTypeService transportTypeService = new TransportTypeServiceImpl();
        TransportType transportType = transportTypeService.getByName(transportTypeString);
        order.setIdTransportType(transportType.getId());
        order.setPrice(new BigDecimal(4));
    }
}
