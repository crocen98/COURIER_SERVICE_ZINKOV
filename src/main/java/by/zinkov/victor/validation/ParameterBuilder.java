package by.zinkov.victor.validation;

import javax.servlet.http.HttpServletRequest;

public class ParameterBuilder {

    private static ParameterBuilder instance = new ParameterBuilder();
    private ParameterBuilder(){
    }

    public static ParameterBuilder getInstance(){
        return instance;
    }

    public String[] build(HttpServletRequest request, String ... paramsNames){
        String[] params = new String[paramsNames.length];

        for (int i = 0; i < paramsNames.length ; ++i){
            String parameter = request.getParameter(paramsNames[i]);
            params[i] = parameter;
        }
        return params;
    }
}
