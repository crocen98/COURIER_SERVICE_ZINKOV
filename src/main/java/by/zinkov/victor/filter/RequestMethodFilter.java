package by.zinkov.victor.filter;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.CommandEnum;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(filterName = "RequestMethodFilter", urlPatterns = {"/"} , servletNames = {"index"})
public class RequestMethodFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String commandString = request.getParameter("command");
        CommandEnum command = CommandEnum.getByName(commandString);
        if (!command.equals(CommandEnum.PAGE_404)) {
            String requestMethod = httpServletRequest.getMethod();
            if (!requestMethod.equalsIgnoreCase(command.getMethod().toString())) {
                httpServletResponse.sendRedirect(Router.INDEX_ERROR_ROUT + "not_valid_method");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}