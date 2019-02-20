package by.zinkov.victor.service.validation;

import by.zinkov.victor.service.exception.ValidationException;

import java.util.regex.Pattern;

public class StringValidator {

    private static final String INTEGER_PATTERN = "[+-]?(\\d){1,9}";
    private final Pattern integerPattern = Pattern.compile(INTEGER_PATTERN);
    public static final int[] POSITIVE_RANGE = new int[]{0 , Integer.MAX_VALUE};

    public void isMatchesInt(String stringInt, int[] range) throws ValidationException {

        if(stringInt == null){
            throw new ValidationException("String number is null");
        }
        boolean isMatches;
        isMatches = integerPattern.matcher(stringInt).matches();
        if (isMatches) {
            int number = Integer.valueOf(stringInt);
            if (range == null || range.length != 2) {
                throw new IllegalArgumentException("Array length should be equals 2, but it equals");
            }
            if (number < range[0] || number > range[1]) {
                isMatches = false;
            }
        }
        if(!isMatches){
            throw new ValidationException("String: " + stringInt + "not valid");
        }
    }
}
