package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService service = new UserServiceImpl();
        try {
            UserDto userDto = service.LogIn(login,password);
            HttpSession session = request.getSession();
            session.setAttribute("user" , userDto);
          switch (userDto.getUserRole()){
              case ADMINISTRATOR:
                  router.setRoute(Page.ALL_TRANSPORT_TYPES.getRout());
                  router.setType(Router.Type.REDIRECT);
                  break;
              case CLIENT:
                  router.setRoute(Page.START_PAGE.getRout());
              break;
              case COURIER:
                  router.setRoute(Page.START_PAGE.getRout());
                  break;
                  default:
                      throw new UnsupportedOperationException("Congratulations!!! Your find bag");

          }
        } catch (ServiceException e) {
            router.setRoute(Page.LOG_IN.getRout());
            request.setAttribute("error", e.getMessage());
        }
        return router;
    }
}
