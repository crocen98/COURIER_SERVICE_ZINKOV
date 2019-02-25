package by.zinkov.victor.service.validation;

import by.zinkov.victor.service.exception.ValidationException;

import java.util.regex.Pattern;

public class StringValidator {

    private static final String INTEGER_PATTERN = "[+-]?(\\d){1,9}";
    private final Pattern integerPattern = Pattern.compile(INTEGER_PATTERN);
    private static final String EMAIL_PATTERN = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private static final String PHONE_PATTERN = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$";
    private final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    private static final String COORDINATES_PATTERN = "^(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)$";
    private final Pattern coordinatesPattern = Pattern.compile(COORDINATES_PATTERN);

    public static final int[] POSITIVE_RANGE = new int[]{0, Integer.MAX_VALUE};
    private static final StringValidator INSTANCE = new StringValidator();

    public static StringValidator getInstance() {
        return INSTANCE;
    }

    private StringValidator() {
    }

    public void coordinatesMatches(String coordinates) throws ValidationException {
        if (coordinates == null || !coordinatesPattern.matcher(coordinates).matches()) {
            throw new ValidationException("Not valid coordinates!");
        }
    }

    public void phoneMatches(String phone) throws ValidationException {
        if (phone == null || !phonePattern.matcher(phone).matches()) {
            throw new ValidationException("Not valid phone!");
        }
    }

    public void emailMatches(String email) throws ValidationException {
        if (email == null || !emailPattern.matcher(email).matches()) {
            throw new ValidationException("Not valid email!");
        }
    }

    public void simpleStingMatches(String string, int maxLength, String fieldName) throws ValidationException {
        if (string == null || string.length() > maxLength || string.trim().equals("")) {
            throw new ValidationException("not valid " + fieldName + " :" + string);
        }
    }

    public void isMatchesInt(String stringInt, int[] range) throws ValidationException {
        if (stringInt == null) {
            throw new ValidationException("String number is null");
        }
        boolean isMatches;
        isMatches = integerPattern.matcher(stringInt).matches();
        if (isMatches) {
            int number = Integer.parseInt(stringInt);
            if (range == null || range.length != 2) {
                throw new IllegalArgumentException("Array length should be equals 2, but it equals");
            }
            if (number < range[0] || number > range[1]) {
                isMatches = false;
            }
        }
        if (!isMatches) {
            throw new ValidationException("String: " + stringInt + "not valid");
        }
    }
}
