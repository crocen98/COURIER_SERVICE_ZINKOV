package by.zinkov.victor.command.exception;

public class CommandException extends Exception {
    public CommandException(String msg , Exception exception){
        super(msg,exception);
    }
    public CommandException(String msg ){
        super(msg);
    }
}