package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand extends Command {
//    private static final String SESSION_ATTRIBUTE = "user";
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(Page.START_PAGE.getRout());
        HttpSession session = request.getSession();
        session.invalidate();
//        session.setAttribute(SESSION_ATTRIBUTE, null);
        return router;
    }
}
