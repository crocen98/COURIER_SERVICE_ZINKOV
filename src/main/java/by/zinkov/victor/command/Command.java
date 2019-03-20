package by.zinkov.victor.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Command
 */
public abstract class Command {

    /**
     * Execute command
     *
     * @param request is used for extracting request parameters
     * @return response content
     */
    public abstract Router execute(HttpServletRequest request) throws CommandException;


    protected Map<String, String> readParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }
}
