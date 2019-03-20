package by.zinkov.victor.service;

/**
 * Service Exception
 */
public class ServiceException extends Exception {
    private String errorKey = "unidentified";

    public String getErrorKey() {
        return errorKey + ".error";
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause){
        super(cause);
    }
    public ServiceException(String msg){
        super(msg);
    }
}
