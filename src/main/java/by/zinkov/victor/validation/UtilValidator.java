package by.zinkov.victor.validation;

import java.util.regex.Pattern;

public class UtilValidator {


    private static final String DECIMAL_14_2_REGEX = "^[+-]?(\\d){1,14}(\\.(\\d){1,2})|$";
    private final Pattern DECIMAL_14_2_PATTERN = Pattern.compile(DECIMAL_14_2_REGEX);


    private static final String SIMPLE_STRING_REGEX = "^(\\w|\\d| |-|[a-яА-Я]){1,35}$";
    private final Pattern SIMPLE_STRING_PATTERN = Pattern.compile(SIMPLE_STRING_REGEX);


    private static final String PASSWORD_REGEX = "^(\\w|\\d|){64}$";
    private final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);


    private static final String INTEGER_REGEX = "[+-]?(\\d){1,9}";
    private final Pattern INTEGER_PATTERN = Pattern.compile(INTEGER_REGEX);

    private static final String EMAIL_REGEX = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
    private final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static final String PHONE_REGEX = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$";
    private final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    private static final String COORDINATES_REGEX = "^(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)$";
    private final Pattern COORDINATES_PATTERN = Pattern.compile(COORDINATES_REGEX);

    public static final int[] POSITIVE_RANGE = new int[]{0, Integer.MAX_VALUE};


    private static final UtilValidator INSTANCE = new UtilValidator();

    public static UtilValidator getInstance() {
        return INSTANCE;
    }

    private UtilValidator() {
    }

    public boolean coordinatesMatches(String coordinates) {
        return coordinates != null && COORDINATES_PATTERN.matcher(coordinates).matches();
    }

    public boolean phoneMatches(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public boolean emailMatches(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean passwordHashMatches(String pass) {
        return pass != null &&
                PASSWORD_PATTERN.matcher(pass).matches();
    }

    public boolean simpleStingMatches(String string, int maxLength) {
        return string != null && string.length() < maxLength
                &&
                SIMPLE_STRING_PATTERN.matcher(string).matches();
    }

    public boolean isMatchesInt(String stringInt, int[] range) {
        if (stringInt == null) {
            return false;
        }
        boolean isMatches;
        isMatches = INTEGER_PATTERN.matcher(stringInt).matches();
        if (isMatches) {
            int number = Integer.parseInt(stringInt);
            if (range == null || range.length != 2) {
                throw new IllegalArgumentException("Array length should be equals 2, but it equals");
            }
            if (number < range[0] || number > range[1]) {
                isMatches = false;
            }
        }
        return isMatches;
    }


    public boolean isMatchesDecimal(String number) {
        if (number == null) {
            return false;
        }
        return DECIMAL_14_2_PATTERN.matcher(number).matches();
    }
}
