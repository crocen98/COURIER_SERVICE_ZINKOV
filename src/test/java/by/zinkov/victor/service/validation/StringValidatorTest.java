package by.zinkov.victor.service.validation;

import org.junit.Test;


public class StringValidatorTest {
    private static final StringValidator validator = StringValidator.getInstance();

    @Test(expected = ValidationException.class)
    public void shouldGetNullAndReturnFalse() throws ValidationException {
        final String testNullableString = null;
        validator.isMatchesInt(testNullableString, new int[]{1, 2});

    }

    @Test(expected = ValidationException.class)
    public void shouldGetStringWithCountOfNumbersBiggerThanTenAndReturnFalse() throws ValidationException {
        final String testString = "+12445643646";
        validator.isMatchesInt(testString, new int[]{1, 2});
    }

    @Test
    public void getValidStringsWithPlusAndMinus() throws ValidationException {
        final String testStringOne = "+124456436";
        final String testStringTwo = "-124456436";

         validator.isMatchesInt(testStringOne, new int[]{-1244564999, 1244564364});
         validator.isMatchesInt(testStringTwo, new int[]{-1244564999, 1244564364});

    }

    @Test(expected = ValidationException.class)
    public void getValidStringButItDonSituatedInRange() throws ValidationException {
        final String testStringOne = "+124456436";
        final String testStringTwo = "-124456436";

       validator.isMatchesInt(testStringOne, new int[]{1, 100});
       validator.isMatchesInt(testStringTwo, new int[]{-1, -100});

    }

    @Test(expected = IllegalArgumentException.class)
    public void getIllegalRangeArray() throws ValidationException{
        final String testStringOne = "+124456436";
        int[] testArray = {1, 100, 2};
        validator.isMatchesInt(testStringOne, testArray);
    }

}