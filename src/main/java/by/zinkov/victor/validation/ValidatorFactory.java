package by.zinkov.victor.validation;

import by.zinkov.victor.validation.impl.*;

public class ValidatorFactory {
    private static ValidatorFactory ourInstance = new ValidatorFactory();

    public static ValidatorFactory getInstance() {
        return ourInstance;
    }

    private ValidatorFactory() {
    }

    public SignUpValidator getSignUpValidator() {
        return new SignUpValidator();
    }

    public CreateOrderFirstStageValidator getCreateOrderFirstStageValidator() {
        return new CreateOrderFirstStageValidator();
    }

    public SendRestorePasswordTokenValidator getSendRestorePasswordTokenValidator(){
        return new SendRestorePasswordTokenValidator();
    }

    public NewTransportTypeForCourierValidator getNewTransportTypeForCourierValidator() {
        return new NewTransportTypeForCourierValidator();
    }

    public AddNewCargoTypeForCourierValidator getAddNewCargoTypeForCourierValidator(){
        return new AddNewCargoTypeForCourierValidator();
    }

}
