package by.zinkov.victor.controller.command;

import by.zinkov.victor.dto.ResponseContent;
import by.zinkov.victor.service.ServiceFactory;
import by.zinkov.victor.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Example of the command implementation
 */
public class CommandExample implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        // Provide your code here

        UserService userService = ServiceFactory.getInstance().getUserService();

        // Provide your code here

        throw new UnsupportedOperationException();
    }
}
