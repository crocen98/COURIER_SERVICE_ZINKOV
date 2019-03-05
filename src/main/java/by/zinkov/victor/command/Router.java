package by.zinkov.victor.command;

/**
 * Provide route to jsp page
 */
public class Router {
    private String route;
    public static final String INDEX_ROUT = "/couriers";
    public static final String INDEX_ERROR_ROUT = "/couriers?error=";
    private Type type = Type.FORWARD;
    public enum Type {
        FORWARD, REDIRECT
    }

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
