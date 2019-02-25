package by.zinkov.victor.controller.filter;

import by.zinkov.victor.controller.command.AccessLevel;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebFilter(filterName="AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)   {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
        if (userDto != null) {
            UserService service = new UserServiceImpl();
            try {
                UserDto userFromDb = service.getByPK(userDto.getId());
                if(userFromDb.getUserStatus() == UserStatus.WAITING_CONFIRMATION){
                    httpServletResponse.sendRedirect(Router.INDEX_ERROR_ROUT + "WAITING_CONFIRMATION");
                    httpServletRequest.getSession().setAttribute("user", null);
                } else if(userFromDb.getUserStatus() == UserStatus.BLOCKED){
                    httpServletResponse.sendRedirect(Router.INDEX_ERROR_ROUT + "BLOCKED");
                    httpServletRequest.getSession().setAttribute("user", null);
                }

            } catch (ServiceException e) {
                throw new IllegalStateException("Illegal state of program! Problem in UserService",e);
            }
        }
        String command = request.getParameter("command");
        if (command == null && userDto != null) {
            request.getRequestDispatcher(Page.START_AUTHORIZED_PAGE.getRout()).forward(request, response);
        } else if (command == null) {
            request.getRequestDispatcher(Page.START_PAGE.getRout()).forward(request, response);

        } else if (userDto == null && Arrays.stream(CommandEnum.getByName(command).
                getLevels()).
                noneMatch((ob) -> ob == AccessLevel.VISITOR || ob == AccessLevel.ALL)) {
            request.getRequestDispatcher(Page.START_PAGE.getRout()).forward(request, response);
        } else if (userDto != null && Arrays.stream
                (CommandEnum.getByName(command).getLevels()).
                noneMatch((ob) -> userDto.getUserRole().toString().equals(ob.toString()) || ob == AccessLevel.ALL)) {
            request.getRequestDispatcher(Page.START_AUTHORIZED_PAGE.getRout()).forward(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}