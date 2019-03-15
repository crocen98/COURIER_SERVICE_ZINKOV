package by.zinkov.victor.command;

public class CommandException extends Exception {
    public CommandException(String msg , Exception exception){
        super(msg,exception);
    }
    public CommandException(String msg ){
        super(msg);
    }
}
