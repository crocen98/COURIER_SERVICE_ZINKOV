package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    private static final String SESSION_ATTRIBUTE = "user";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        router.setType(Router.Type.FORWARD);
        router.setRoute("index.jsp");
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_ATTRIBUTE, null);
        return router;
    }
}
