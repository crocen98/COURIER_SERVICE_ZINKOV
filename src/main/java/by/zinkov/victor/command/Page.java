package by.zinkov.victor.command;

public enum Page {
    ERROR("/WEB-INF/pages/error.jsp"),
    LOG_IN("/WEB-INF/pages/log_in.jsp"),
    SIGN_UP("/WEB-INF/pages/sign_up.jsp"),
    ALL_TRANSPORT_TYPES("/WEB-INF/pages/all_transport_types.jsp"),
    START_PAGE("/WEB-INF/pages/main.jsp"),
    ACTIVATE_PAGE("/WEB-INF/pages/activate.jsp"),
    FORGOT_PASSWORD("/WEB-INF/pages/forgot_password.jsp"),
    START_AUTHORIZED_PAGE("/WEB-INF/pages/client_main.jsp"),
    CHANGE_PASSWORD("/WEB-INF/pages/change_password.jsp"),
    CREATE_ORDER("/WEB-INF/pages/create_order.jsp"),
    CREATE_ORDER_SECOND_STAGE("/WEB-INF/pages/create_order_second_stage.jsp"),
    LABEL_FOR_ERROR_INPUT("/WEB-INF/frames/label_for_error.jsp"),
    ORDER_PAGE("/WEB-INF/pages/user_order.jsp"),
    INDEX("index.jsp");



    private final String rout;

    Page(String rout) {
        this.rout = rout;
    }

    public String getRout() {
        return rout;
    }
}
