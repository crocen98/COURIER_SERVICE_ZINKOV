package by.zinkov.victor.controller.command;

import by.zinkov.victor.controller.RequestMethod;

public enum CommandEnum {
    ADD_TRANSPORT_TYPE("add_transport_type" , RequestMethod.POST,new AccessLevel[] {AccessLevel.ADMINISTRATOR}),
    TO_LOG_IN_PAGE("to_log_in_page", RequestMethod.GET, new AccessLevel[] {AccessLevel.VISITOR}),
    DELETE_TRANSPORT_TYPE("delete_transport_type", RequestMethod.POST, new AccessLevel[] {AccessLevel.ADMINISTRATOR}),
    SIGN_UP("sign_up", RequestMethod.POST, new AccessLevel[] {AccessLevel.VISITOR}),
    ALL_TRANSPORT_TYPES("all_transport_types" , RequestMethod.GET, new AccessLevel[] {AccessLevel.ADMINISTRATOR}),
    CHANGE_TRANSPORT_TYPE("change_transport_type", RequestMethod.GET, new AccessLevel[] {AccessLevel.ADMINISTRATOR}),
    REGISTER_COMMAND("register_command", RequestMethod.POST, new AccessLevel[] { AccessLevel.VISITOR}),
    LOG_IN("log_in", RequestMethod.POST,new AccessLevel[] {AccessLevel.VISITOR} ),
    ACTIVATE("activate", RequestMethod.GET, new AccessLevel[] { AccessLevel.VISITOR}),
    START_PAGE("start_page",RequestMethod.GET,new AccessLevel[] {AccessLevel.ALL});



    private final String name;
    private final RequestMethod method;
    private final AccessLevel[] levels;
     CommandEnum(String name, RequestMethod expectedMethod, AccessLevel[] levels){
        this.name = name;
        this.method = expectedMethod;
        this.levels = levels;
    }

    public static CommandEnum getByName(String name){
         return CommandEnum.valueOf(name.toUpperCase());
    }

    public AccessLevel[] getLevels() {
        return levels;
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
