package by.zinkov.victor.controller.command;

public enum Page {
    ERROR("/WEB-INF/pages/error.jsp");

    private final String rout;

    Page(String rout) {
        this.rout = rout;
    }

    public String getRout() {
        return rout;
    }
}
