package by.zinkov.victor.controller.command.impl;

import by.zinkov.victor.controller.command.Command;
import by.zinkov.victor.controller.command.Router;
import by.zinkov.victor.controller.command.exception.CommandException;
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
