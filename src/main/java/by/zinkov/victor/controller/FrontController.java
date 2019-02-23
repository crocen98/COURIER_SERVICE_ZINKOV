package by.zinkov.victor.controller;

import by.zinkov.victor.controller.command.*;
import by.zinkov.victor.controller.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns ="/couriers")
public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);
    private static final String COMMAND_REQUEST_PARAMETER = "command";
    private static final String ERROR = "error";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        request.getSession();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info(request.getRequestURI());
        String commandName = request.getParameter(COMMAND_REQUEST_PARAMETER);
        CommandEnum commandEnum = CommandEnum.getByName(commandName);
        Command command = CommandProvider.getInstance().takeCommand(commandEnum);
        System.out.println("WORK   " + commandName);
        Router router= null;
        try {
             router = command.execute(request);
        } catch (CommandException e) {
            LOGGER.error(e);
            request.setAttribute(ERROR,e.getMessage());
            router.setType(Router.Type.FORWARD);
            router.setRoute(Page.ERROR.getRout());
        }

        String page = router.getRoute();
        if (router.getType() == Router.Type.REDIRECT) {
            response.sendRedirect(page);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        }
    }
}
