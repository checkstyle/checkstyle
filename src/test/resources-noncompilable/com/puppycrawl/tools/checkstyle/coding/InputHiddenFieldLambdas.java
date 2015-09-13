package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InputHiddenFieldLambdas {

    /**
     * Example 1: lambda parameter 'value' on line 16
     * hides a field 'value' on line 14.
     */
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    Integer value = new Integer(1);
    {
        numbers.forEach((Integer value) -> System.out.println(value)); // violation ('value' hides a field)
    }

    /**
     * Example 2: lambda parameter 'element' on line 27
     * does not hide a field 'element' on line 25, because
     * field 'element' can not be referenced from a static context.
     */
    static List<String> names = Arrays.asList("Andrei", "Michal", "Roman", "Vladislav");
    Integer element = new Integer(1);
    static {
        names.forEach((String element) -> System.out.println(element));
    }

    /**
     * Example 3: lambda parameter 'element1' on line 38 (which type is omitted)
     * does not hide a field 'element1' on line 36, because
     * field 'element1' can not be referenced from a static context.
     */
    static List<String> names = Arrays.asList("Andrei", "Michal", "Roman", "Vladislav");
    Integer element1 = new Integer(1);
    static {
        names.forEach(element1 -> System.out.println(element1));
    }

    /**
     * Example 4: lambda parameter 'languageCode' on line 48
     * hides a field 'languageCode' on line 46.
     */
    static List<String> languageCodes = Arrays.asList("de", "ja", "fr", "pt");
    static String languageCode = new String();
    {
        names.forEach(languageCode -> System.out.println(languageCode)); // violation ('languageCode' hides a field)
    }

    /**
     * Example 5: lambda parameter 'num1' on line 57
     * hides a field 'num1' on line 55.
     */
    int num1 = 1;
    Optional<Boolean> foo1(int i) {
        return Optional.of(5).map(num1 -> { // violation ('num1' hides a field)
            if (num1 == 1) return true;
            else if (num1 == 2) return true;
            else return false;
        });
    }

    /**
     * Example 6: lambda parameter 'num2' on line 70
     * hides a field 'num2' on line 68.
     */
    static int num2 = 1;
    Optional<Boolean> foo2(int i) {
        return Optional.of(5).map(num2 -> { // violation ('num2' hides a field)
            if (num2 == 1) return true;
            else if (num2 == 2) return true;
            else return false;
        });
    }

    /**
     * Example 7: lambda parameter 'num3' on line 84
     * does not hide a field 'num3' on line 82,
     * because field 'num3' can not be referenced from a static context.
     */
    int num3 = 1;
    static Optional<Boolean> foo3(int i) {
        return Optional.of(5).map(num3 -> {
            if (num3 == 1) return true;
            else if (num3 == 2) return true;
            else return false;
        });
    }

    /**
     * Example 8: lambda parameter 'lc1' on line 98
     * hides a field 'lc1' on line 95.
     */
    static String lc1 = new String();
    private void foo4() {
        List<String> lcs = Arrays.asList("de", "ja", "fr", "pt");
        names.forEach(lc1 -> System.out.println(lc1)); // violation ('lc1' hides a field)
    }

    /**
     * Example 9: lambda parameter 'lc2' on line 109
     * does not hide a field 'lc2' on line 106, because
     * field 'lc2' can not be referenced from a static context.
     */
    String lc2 = new String();
    private static void foo5() {
        List<String> lcs = Arrays.asList("de", "ja", "fr", "pt");
        names.forEach(lc2 -> System.out.println(lc2));
    }
}
