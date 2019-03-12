package by.zinkov.victor.validation;

public class ErrorMessage {

    String messageKey;
    Object[] args;

    public ErrorMessage(String messageKey, Object... args) {
        this.messageKey = messageKey;
        this.args = args;
    }
}
