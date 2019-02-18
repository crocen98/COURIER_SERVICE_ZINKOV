package by.zinkov.victor.controller.command.exception;

public class NoSuchCommandException extends RuntimeException {

    public NoSuchCommandException(String msg){
        super(msg);
    }
}
