package by.zinkov.victor.command.impl.user;

import by.zinkov.victor.domain.Order;
import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.impl.OrderServiceImpl;
import by.zinkov.victor.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FinishCreatingOrder extends Command {
    private static final Logger LOGGER = LogManager.getLogger(FinishCreatingOrder.class);

    private static final String ORDER_ATTRIBUTE = "order";
    private static final String COURIER_ID_PARAMETER = "courier_id";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(Router.INDEX_ROUT);
        String courierId = request.getParameter(COURIER_ID_PARAMETER);
        UtilValidator validator = UtilValidator.getInstance();
        HttpSession session = request.getSession();

        Order order = (Order) session.getAttribute(ORDER_ATTRIBUTE);
        try {
            validator.isMatchesInt(courierId, UtilValidator.POSITIVE_RANGE);
            order.setIdCourier(Integer.valueOf(courierId));
            order.setIdStatus(OrderStatus.ORDERED.getId());
            OrderService orderService = new OrderServiceImpl();
            orderService.save(order);

        } catch (ServiceException e) {
            LOGGER.error(e);
            router.setRoute(Router.INDEX_ERROR_ROUT + e.getErrorKey());
        } finally {
            session.setAttribute(ORDER_ATTRIBUTE, null);
        }
        return router;
    }
}
