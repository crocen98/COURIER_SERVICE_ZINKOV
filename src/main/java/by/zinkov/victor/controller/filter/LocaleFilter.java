package by.zinkov.victor.controller.filter;

import by.zinkov.victor.controller.command.AccessLevel;
import by.zinkov.victor.controller.command.CommandEnum;
import by.zinkov.victor.controller.command.Page;
import by.zinkov.victor.dto.UserDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;


@WebFilter(filterName="LocaleFilter")
public class LocaleFilter  implements Filter {
    private static final String LOCALE_SESSION_ATTRIBUTE  = "locale";
    private static final String ENGLISH_LANGUAGE = "en";
    @Override
    public void init(FilterConfig filterConfig)   {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpSession session = httpServletRequest.getSession();
        String locale = (String) session.getAttribute(LOCALE_SESSION_ATTRIBUTE);
        if(locale == null){
            session.setAttribute(LOCALE_SESSION_ATTRIBUTE, ENGLISH_LANGUAGE);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}