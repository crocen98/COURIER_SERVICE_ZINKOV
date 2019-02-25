package by.zinkov.victor.controller.command;

import by.zinkov.victor.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Command
 */
public interface Command {

    /**
     * Execute command
     * @param request is used for extracting request parameters
     * @return response content
     */
    Router execute(HttpServletRequest request) throws CommandException;
}
