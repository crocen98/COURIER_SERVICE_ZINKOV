package by.zinkov.victor.controller.filter;



import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.dto.UserDto;
import by.zinkov.victor.service.UserService;
import by.zinkov.victor.service.exception.ServiceException;
import by.zinkov.victor.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName="UserStatusFilter")
public class UserStatusFilter implements Filter {
    private static final String USER_SESSION_ATTRIBUTE  = "user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        if (userDto != null) {
            UserService service = new UserServiceImpl();
            try {
                UserDto userFromDb = service.getByPK(userDto.getId());
                if(userFromDb.getUserStatus() == UserStatus.WAITING_CONFIRMATION){
                    httpServletResponse.sendRedirect(Router.INDEX_ERROR_ROUT + UserStatus.WAITING_CONFIRMATION);
                    httpServletRequest.getSession().setAttribute(USER_SESSION_ATTRIBUTE, null);
                } else if(userFromDb.getUserStatus() == UserStatus.BLOCKED){
                    httpServletResponse.sendRedirect(Router.INDEX_ERROR_ROUT + UserStatus.BLOCKED);
                    httpServletRequest.getSession().setAttribute(USER_SESSION_ATTRIBUTE, null);
                }
            } catch (ServiceException e) {
                throw new IllegalStateException("Illegal state of program! Problem in UserService",e);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}