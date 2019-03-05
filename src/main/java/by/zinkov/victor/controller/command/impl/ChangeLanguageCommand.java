package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String LOCALE_SESSION_ATTRIBUTE  = "locale";
    private static final String ENGLISH_LANGUAGE = "en";
    private static final String RUSSIAN_LANGUAGE = "ru";




    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        router.setRoute(Router.INDEX_ROUT);
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE_SESSION_ATTRIBUTE);
        if(locale.equals(ENGLISH_LANGUAGE)){
            session.setAttribute(LOCALE_SESSION_ATTRIBUTE,RUSSIAN_LANGUAGE);
        } else {
            session.setAttribute(LOCALE_SESSION_ATTRIBUTE,ENGLISH_LANGUAGE);
        }
        return router;
    }
}
