package by.zinkov.victor.command.impl.user;

import by.zinkov.victor.builder.BuilderFactory;
import by.zinkov.victor.builder.impl.OrderBuilder;
import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.*;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.impl.UserServiceImpl;
import by.zinkov.victor.validation.ValidatorFactory;
import by.zinkov.victor.validation.impl.CreateOrderFirstStageValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.zinkov.victor.domain.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateOrderFirstStageStage extends Command {
    private static final Logger LOGGER = LogManager.getLogger(CreateOrderFirstStageStage.class);
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CARGO_TYPE_ATTRIBUTE = "cargo_type";
    private static final String TRANSPORT_TYPE_ATTRIBUTE = "transport_type";
    private static final String COURIERS_ATTRIBUTE = "couriers";
    private static final String DISTANCE_ATTRIBUTE = "distance";
    private static final int MAX_RADIUS_OF_DETECTING_COURIER = 40;


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.CREATE_ORDER_SECOND_STAGE.getRout());
        HttpSession session = request.getSession();
        session.setAttribute(ORDER_ATTRIBUTE, null);

        UserDto user = (UserDto) session.getAttribute(USER_ATTRIBUTE);
        Map<String, String> parameters = readParameters(request);
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        CreateOrderFirstStageValidator createOrderFirstStageValidator = validatorFactory.getCreateOrderFirstStageValidator();
        Map<String, String> errors = createOrderFirstStageValidator.validate(parameters);
        if (errors.size() != 0) {
            LOGGER.warn("User enter not valid data it maybe bug or he is hacks us. User info:\n" + user);
            session.setAttribute(ORDER_ATTRIBUTE, null);

            initRouterForFaildValidation(router, request, errors);

            return router;
        }

        request.setAttribute(TRANSPORT_TYPE_ATTRIBUTE, parameters.get(TRANSPORT_TYPE_ATTRIBUTE));
        request.setAttribute(CARGO_TYPE_ATTRIBUTE, parameters.get(CARGO_TYPE_ATTRIBUTE));

        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            OrderStatusService orderStatusService = factory.getOrderStatusService();


            if (orderStatusService.haveActiveOrder(user.getId())) {
                router.setType(Router.Type.REDIRECT);
                router.setRoute(CommandEnum.TO_USER_ORDER_PAGE.getUrlWithError("have_active_order.error"));
                return router;
            }

            BuilderFactory builderFactory = BuilderFactory.getInstance();
            OrderBuilder orderBuilder = builderFactory.getOrderBuilder();
            Order order = orderBuilder.build(parameters);
            order.setIdCustomer(user.getId());
            session.setAttribute(ORDER_ATTRIBUTE, order);

            UserService userService = new UserServiceImpl();
            Map<User, Double> couriers = userService.getCouriersByParams(parameters.get(TRANSPORT_TYPE_ATTRIBUTE), parameters.get(CARGO_TYPE_ATTRIBUTE));
            couriers = couriers.entrySet().stream().filter(item ->
                    calculateDistance(item.getKey().getLocation(), order.getStartPoint()) <= MAX_RADIUS_OF_DETECTING_COURIER
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            request.setAttribute(COURIERS_ATTRIBUTE, couriers);

            double distance = calculateDistance(order);
            request.setAttribute(DISTANCE_ATTRIBUTE, distance);

            TransportTypeService transportTypeService = factory.getTransportTypeService();
            TransportType transportType = transportTypeService.getById(order.getIdTransportType());
            BigDecimal price = BigDecimal.valueOf(transportType.getCoefficient().doubleValue() * distance);
            order.setPrice(price);

        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(ORDER_ATTRIBUTE, null);
            router.setType(Router.Type.REDIRECT);
            router.setRoute(CommandEnum.TO_CREATE_ORDER_PAGE.getUrlWithError(e.getErrorKey()));
        }
        return router;
    }

    private double calculateDistance(Order order) {
        DistanceService service = new DistanceService();
        return service.calculate(order.getStartPoint(), order.getFinishPoint());
    }

    private double calculateDistance(String order, String courier) {
        DistanceService service = new DistanceService();
        return service.calculate(order, courier);
    }
}
