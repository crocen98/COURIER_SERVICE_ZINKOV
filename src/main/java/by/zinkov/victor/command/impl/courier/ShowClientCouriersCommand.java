package by.zinkov.victor.command.impl.courier;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ShowClientCouriersCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
