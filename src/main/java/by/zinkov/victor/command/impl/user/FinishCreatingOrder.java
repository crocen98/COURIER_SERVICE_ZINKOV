package by.zinkov.victor.command.impl.user;

import by.zinkov.victor.domain.Order;
import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.domain.OrderStatus;
import by.zinkov.victor.service.OrderService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.validation.ValidationException;
import by.zinkov.victor.service.impl.OrderServiceImpl;
import by.zinkov.victor.service.validation.StringValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FinishCreatingOrder implements Command {
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String COURIER_ID_PARAMETER = "courier_id";

    @Override
    public Router execute(HttpServletRequest request)  {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(Router.INDEX_ROUT);
        String courierId = request.getParameter(COURIER_ID_PARAMETER);
        StringValidator validator = StringValidator.getInstance();
        HttpSession session = request.getSession();

        Order order = (Order)session.getAttribute(ORDER_ATTRIBUTE);
        try {
            validator.isMatchesInt(courierId , StringValidator.POSITIVE_RANGE);
            order.setIdCourier(Integer.valueOf(courierId));
            order.setIdStatus(OrderStatus.ORDERED.getId());

            OrderService orderService = new OrderServiceImpl();
            orderService.save(order);

        } catch (ValidationException e) {
            e.printStackTrace();
            router.setRoute(Router.INDEX_ERROR_ROUT + "Error with validation");

        } catch (ServiceException e) {
            e.printStackTrace();
            router.setRoute(Router.INDEX_ERROR_ROUT + "Error with dao");
        } finally {
            session.setAttribute(ORDER_ATTRIBUTE,null);
        }
        return router;
    }
}
