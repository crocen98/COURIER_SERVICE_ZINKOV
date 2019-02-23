package by.zinkov.victor.controller.command;

import by.zinkov.victor.controller.RequestMethod;

public enum CommandEnum {
    ADD_TRANSPORT_TYPE("add_transport_type" , RequestMethod.POST),
    TO_LOG_IN_PAGE("to_log_in_page", RequestMethod.GET),
    DELETE_TRANSPORT_TYPE("delete_transport_type", RequestMethod.POST),
    SIGN_UP("sign_up", RequestMethod.POST),
    ALL_TRANSPORT_TYPES("all_transport_types" , RequestMethod.GET),
    CHANGE_TRANSPORT_TYPE("change_transport_type", RequestMethod.GET),
    REGEISTER_COMMAND("register_command", RequestMethod.POST),
    LOG_IN("log_in", RequestMethod.POST);



    private final String name;
    private final RequestMethod method;
     CommandEnum(String name, RequestMethod expectedMethod){
        this.name = name;
        this.method = expectedMethod;
    }

    public static CommandEnum getByName(String name){
         return CommandEnum.valueOf(name.toUpperCase());
    }

    public String getUrl(){
         return "/couriers/couriers?command="+ name;
    }

    public String getUrlWithError(String error){
        return getUrl() + "&error=" + error;
    }
    @Override
    public String toString() {
        return "CommandEnum{" +
                "name='" + name + '\'' +
                ", method=" + method +
                '}';
    }
}
