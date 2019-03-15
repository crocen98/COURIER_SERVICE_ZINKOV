package by.zinkov.victor.command;

public class NoSuchCommandException extends RuntimeException {

    public NoSuchCommandException(String msg){
        super(msg);
    }
}
