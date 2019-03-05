package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Page;
import by.zinkov.victor.command.Router;

import javax.servlet.http.HttpServletRequest;

public class GoPageCommand implements Command {

    private Page page;
    public GoPageCommand(Page page){
        this.page = page;
    }
    @Override
    public Router execute(HttpServletRequest request)   {
        Router router = new Router();
        router.setType(Router.Type.FORWARD);
        router.setRoute(page.getRout());

        return router;
    }
}
