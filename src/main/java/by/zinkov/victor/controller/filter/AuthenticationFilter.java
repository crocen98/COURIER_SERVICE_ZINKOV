package by.zinkov.victor.controller.filter;

import by.zinkov.victor.controller.command.AccessLevel;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;

import by.zinkov.victor.dto.UserDto;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@WebFilter(filterName="AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    private static final String USER_SESSION_ATTRIBUTE  = "user";
    private static final String COMMAND_PARAMETER = "command";
    @Override
    public void init(FilterConfig filterConfig)   {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        String command = request.getParameter(COMMAND_PARAMETER);
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