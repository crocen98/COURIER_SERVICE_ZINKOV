package by.zinkov.victor.validation;

public class ValidatorFactory {
    private static ValidatorFactory ourInstance = new ValidatorFactory();

    public static ValidatorFactory getInstance() {
        return ourInstance;
    }

    private ValidatorFactory() {
    }

    public SignUpValidator getSignUpValidator(){
        return new SignUpValidator();
    }
}
