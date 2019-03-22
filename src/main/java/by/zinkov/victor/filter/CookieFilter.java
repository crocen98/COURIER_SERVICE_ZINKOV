package by.zinkov.victor.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@WebFilter(filterName = "CookieFilter", urlPatterns = {"/*"})
public class CookieFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(request.getServletContext().getContextPath() + "FILTER");

        if (request instanceof HttpServletRequest) {
            String lang = request.getParameter("lang");
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession();
            if (("en".equalsIgnoreCase(lang) || "ru".equalsIgnoreCase(lang))) {
                session.setAttribute("lang", lang);
//                Cookie langCookie = new Cookie("lang", lang);
//                langCookie.setPath(httpRequest.getContextPath());
//                ((HttpServletResponse) response).addCookie(langCookie);
//                (request).setAttribute("lang", lang);
            } else {
                Optional<String> langAttr = Optional.ofNullable((String) session.getAttribute("lang"));
                if (!langAttr.isPresent()) {
                    session.setAttribute("lang", "en");
                }
//                Optional<Cookie[]> cookies = Optional.ofNullable(httpRequest.getCookies());
//                Cookie langCookie = cookies.map(Stream::of).orElse(Stream.empty())
//                        .filter(cookie -> cookie.getName()
//                                .equalsIgnoreCase("lang"))
//                        .findFirst()
//                        .orElse(new Cookie("lang", "en"));
//
//                langCookie.setPath(httpRequest.getContextPath());
//                ((HttpServletResponse) response).addCookie(langCookie);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
