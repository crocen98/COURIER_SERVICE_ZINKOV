package by.zinkov.victor.service;

/**
 * Service Exception
 */
public class ServiceException extends Exception {
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
