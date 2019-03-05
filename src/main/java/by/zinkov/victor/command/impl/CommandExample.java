package by.zinkov.victor.command.impl;

import by.zinkov.victor.command.Command;
import by.zinkov.victor.command.Router;
import by.zinkov.victor.command.exception.CommandException;
import by.zinkov.victor.service.factory.ServiceFactory;
import by.zinkov.victor.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Example of the command implementation
 */
public class CommandExample implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        // Provide your code here

        UserService userService = ServiceFactory.getInstance().getUserService();

        // Provide your code here

        throw new UnsupportedOperationException();
    }
}
