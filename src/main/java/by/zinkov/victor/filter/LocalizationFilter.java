package by.zinkov.victor.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "CookieFilter", urlPatterns = {"/*"})
public class LocalizationFilter implements Filter {

    private static final String LANG_PARAMETER = "lang";
    private static final String RUSSIAN = "ru";
    private static final String ENGLISH = "en";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String lang = request.getParameter(LANG_PARAMETER);
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession();
            if ((ENGLISH.equalsIgnoreCase(lang) || RUSSIAN.equalsIgnoreCase(lang))) {
                session.setAttribute(LANG_PARAMETER, lang);
            } else {
                Optional<String> langAttr = Optional.ofNullable((String) session.getAttribute(LANG_PARAMETER));
                if (!langAttr.isPresent()) {
                    session.setAttribute(LANG_PARAMETER, ENGLISH);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}