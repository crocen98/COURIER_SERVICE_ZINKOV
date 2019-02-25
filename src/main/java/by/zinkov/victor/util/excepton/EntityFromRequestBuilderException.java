package by.zinkov.victor.util.excepton;

public class EntityFromRequestBuilderException extends Exception {
    public EntityFromRequestBuilderException(String msg , Throwable exception){
        super(msg,exception);
    }
}
