package by.zinkov.victor.validation;

import java.util.regex.Pattern;

public class UtilValidator {

    private static final String INTEGER_REXEX = "[+-]?(\\d){1,9}";
    private final Pattern INTEGER_PATTERN = Pattern.compile(INTEGER_REXEX);

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

    public boolean simpleStingMatches(String string, int maxLength) {
        return string != null && string.length() < maxLength && !string.trim().equals("");
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
}
