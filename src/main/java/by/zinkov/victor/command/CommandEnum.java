package by.zinkov.victor.command;

import by.zinkov.victor.controller.RequestMethod;

import java.util.Arrays;

public enum CommandEnum {
    ADD_TRANSPORT_TYPE("add_transport_type", RequestMethod.POST, AccessLevel.ADMINISTRATOR),
    TO_LOG_IN_PAGE("to_log_in_page", RequestMethod.GET, AccessLevel.VISITOR),
    TO_PASSWORD_RECOVERY_PAGE("to_password_recovery_page", RequestMethod.GET, AccessLevel.VISITOR),
    DELETE_TRANSPORT_TYPE("delete_transport_type", RequestMethod.POST, AccessLevel.ADMINISTRATOR),
    SIGN_UP("sign_up", RequestMethod.POST, AccessLevel.VISITOR),
    ALL_TRANSPORT_TYPES("all_transport_types", RequestMethod.GET, AccessLevel.ADMINISTRATOR),
    CHANGE_TRANSPORT_TYPE("change_transport_type", RequestMethod.GET, AccessLevel.ADMINISTRATOR),
    REGISTER_COMMAND("register_command", RequestMethod.POST, AccessLevel.VISITOR),
    LOG_IN("log_in", RequestMethod.POST, AccessLevel.VISITOR),
    ACTIVATE("activate", RequestMethod.GET, AccessLevel.VISITOR),
    START_PAGE("start_page", RequestMethod.GET, AccessLevel.ALL),
    SEND_RESTORE_TOKEN("send_restore_token", RequestMethod.POST, AccessLevel.VISITOR),
    LOG_OUT("log_out", RequestMethod.GET, AccessLevel.ADMINISTRATOR, AccessLevel.CLIENT, AccessLevel.COURIER),
    RESTORE_PASSWORD("restore_password", RequestMethod.POST, AccessLevel.VISITOR),
    TO_CHANGE_PASSWORD_PAGE("to_change_password_page", RequestMethod.GET, AccessLevel.VISITOR),
    TO_CREATE_ORDER_PAGE("to_create_order_page", RequestMethod.GET, AccessLevel.CLIENT),
    CREATE_ORDER_PAGE_SECOND_STAGE("create_order_page_second_stage", RequestMethod.POST, AccessLevel.CLIENT),
    FINISH_CREATING_ORDER("finish_creating_order", RequestMethod.POST, AccessLevel.CLIENT),
    CHECK_LOGIN("check_login", RequestMethod.POST, AccessLevel.VISITOR),
    CHANGE_LANGUAGE("change_language", RequestMethod.GET, AccessLevel.ALL),
    TO_USER_ORDER_PAGE("to_user_order_page", RequestMethod.GET, AccessLevel.CLIENT),
    EDIT_COURIER_PROFILE_PAGE("edit_courier_profile_page", RequestMethod.GET, AccessLevel.COURIER),
    NEW_TRANSPORT_TYPE_FOR_COURIER("new_transport_type_for_courier", RequestMethod.POST, AccessLevel.COURIER),
    ADD_CARGO_TYPE_FOR_COURIER("add_cargo_type_for_courier", RequestMethod.POST, AccessLevel.COURIER),
    NEW_COURIER_POSITION("new_courier_position", RequestMethod.POST, AccessLevel.COURIER),
    DELETE_CARGO_TYPE_FOR_COURIER("delete_cargo_type_for_courier", RequestMethod.POST, AccessLevel.COURIER),
    TO_COURIER_ACTIVE_ORDER_PAGE("to_courier_active_order_page", RequestMethod.GET, AccessLevel.COURIER),
    START_PERFOMING_ORDER_COMMAND("start_perfoming_order_command", RequestMethod.GET, AccessLevel.COURIER),
    FINISH_PERFOMING_ORDER_COMMAND("finish_perfoming_order_command", RequestMethod.GET, AccessLevel.COURIER),
    TO_CLIENT_COURIERS_PAGE("to_client_couriers_page", RequestMethod.GET, AccessLevel.CLIENT),
    SET_USER_MARK("set_user_mark", RequestMethod.POST, AccessLevel.CLIENT),
    PAGE_404("page_404", RequestMethod.GET, AccessLevel.ALL);


    private final String name;
    private final RequestMethod method;
    private final AccessLevel[] levels;

    CommandEnum(String name, RequestMethod expectedMethod, AccessLevel... levels) {
        this.name = name;
        this.method = expectedMethod;
        this.levels = levels;
    }

    public static CommandEnum getByName(String name) {


        return Arrays.stream(CommandEnum.values())
                .filter(cmd-> cmd.name.equalsIgnoreCase(name))
                .findFirst().orElse(CommandEnum.PAGE_404);
    }

    public AccessLevel[] getLevels() {
        return levels;
    }

    public String getUrl() {
        return "/couriers/couriers?command=" + name;
    }

    public String getUrlWithError(String error) {
        return getUrl() + "&error=" + error;
    }
}
