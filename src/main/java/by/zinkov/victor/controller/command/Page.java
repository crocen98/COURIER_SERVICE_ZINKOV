package by.zinkov.victor.controller.command;

public enum Page {
    ERROR("/WEB-INF/pages/error.jsp"),
    LOG_IN("/WEB-INF/pages/log_in.jsp"),
    SIGN_UP("/WEB-INF/pages/sign_up.jsp"),
    ALL_TRANSPORT_TYPES("/WEB-INF/pages/all_transport_types.jsp"),
    START_PAGE("/WEB-INF/pages/main.jsp"),
    ACTIVATE_PAGE("/WEB-INF/pages/activate.jsp"),
    START_AUTHORIZED_PAGE("/WEB-INF/pages/client_main.jsp");;



    private final String rout;

    Page(String rout) {
        this.rout = rout;
    }

    public String getRout() {
        return rout;
    }
}
