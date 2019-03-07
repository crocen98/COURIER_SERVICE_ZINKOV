package by.zinkov.victor.validation;


import java.util.Map;

public interface Validator {
    Map<String, String> validate(String ...  validationParameters);
}
