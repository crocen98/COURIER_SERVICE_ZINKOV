package by.zinkov.victor.service.exception;

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
}
