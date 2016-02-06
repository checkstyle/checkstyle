//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.coding;

import java.lang.Integer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InputHiddenFieldLambdas {
    /**
     * Example 1: lambda parameter 'value' on line 16
     * hides a field 'value' on line 14.
     */
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    Integer value = new Integer(1);
    {
        numbers.forEach((Integer value) -> System.out.println(value)); // 1 violation
    }

    /**
     * Example 2: lambda parameter 'name' on line 27
     * does not hide a field 'name' on line 25, because
     * field 'name' can not be referenced from a static context.
     */
    static List<String> firstNames = Arrays.asList("Andrei", "Michal", "Roman", "Vladislav");
    String name = new String();
    static {
        firstNames.forEach((String name) -> System.out.println(name));
    }

    /**
     * Example 3: lambda parameter 'brand' on line 38 (which type is omitted)
     * does not hide a field 'brand' on line 36, because
     * field 'brand' can not be referenced from a static context.
     */
    static List<String> carBrands = Arrays.asList("BMW", "Mazda", "Volkswagen");
    String brand = new String();
    static {
        carBrands.forEach(brand -> System.out.println(brand));
    }

    /**
     * Example 4: lambda parameter 'languageCode' on line 48
     * hides a field 'languageCode' on line 46.
     */
    static List<String> languageCodes = Arrays.asList("de", "ja", "fr", "pt");
    static String languageCode = new String();
    {
        languageCodes.forEach(languageCode -> System.out.println(languageCode)); // 1 violation
    }

    /**
     * Example 5: lambda parameter 'number' on line 57
     * hides a field 'number' on line 55.
     */
    int number = 1;
    Optional<Object> foo1(int i) {
        return Optional.of(5).map(number -> { // violation
            if (number == 1) return true;
            else if (number == 2) return true;
            else return false;
        });
    }

    /**
     * Example 6: lambda parameter 'id' on line 70
     * hides a field 'id' on line 68.
     */
    static long id = 1;
    Optional<Object> foo2(int i) {
        return Optional.of(5).map(id -> { // violation
            if (id == 1) return true;
            else if (id == 2) return true;
            else return false;
        });
    }

    /**
     * Example 7: lambda parameter 'age' on line 84
     * does not hide a field 'age' on line 82,
     * because field 'age' can not be referenced from a static context.
     */
    int age = 21;
    static Optional<Object> foo3(int i) {
        return Optional.of(5).map(age -> {
            if (age == 1) return true;
            else if (age == 2) return true;
            else return false;
        });
    }

    /**
     * Example 8: lambda parameter 'note' on line 98
     * hides a field 'note' on line 95.
     */
    static String note = new String();
    private void foo4() {
        List<String> acceptableNotes = Arrays.asList("C", "D", "E", "F", "G", "A", "B");
        acceptableNotes.forEach(note -> System.out.println(note)); // 1 violation
    }

    /**
     * Example 9: lambda parameter 'letter' on line 109
     * does not hide a field 'letter' on line 106, because
     * field 'letter' can not be referenced from a static context.
     */
    String letter = new String("a");
    private static void foo5() {
        List<String> acceptableAlphabet = Arrays.asList("a", "b", "c");
        acceptableAlphabet.forEach(letter -> System.out.println(letter));
    }

    @FunctionalInterface
    interface Function <A, B> {
        public B apply (A a, B b);
    }

    /**
     * Example 10: typed parameters - two hide fields
     */
    String stringValue = "248.3";
    int intValue = 2;
    {
        Function <String, Integer> multiAdder = (String stringValue, Integer intValue) -> { // 2 violations
            return Integer.parseInt(stringValue) + intValue;
        };
        System.out.println(multiAdder.apply ("22.4", 2));
    }

    /**
     * Example 11: typed parameters - one hide field
     */
    Double doubleValue = 8.5;
    {
        Function <Integer, Double> adder = (Integer integerValue, Double doubleValue) -> { // 1 violation
            return  integerValue + doubleValue;
        };
        System.out.println(adder.apply(2, 2.2));
    }

    /**
     * Example 11: untyped parameters - two hide fields
     */
    String firstString = "Hello,";
    String secondString = " World!";
    {
        Function <String, String> stringConcat = (firstString, secondString) -> { // 2 violations
            return firstString + secondString;
        };
        System.out.println(stringConcat.apply("A", "B"));
    }

    @FunctionalInterface
    interface SomeFunction<One, Two> {
        public Two apply(One one, Two two);
    }

    /**
     * Example 11: untyped parameters - one hide field
     */
    Integer first = 1;
    {
        Function<Integer, Character> turnToZ = (first, second) -> 'z'; // 1 violation
    }

    @FunctionalInterface
    public interface Foo {
        public String apply();
    }

    /**
     * Example 12: case when no parameters are given
     */
    {
        Foo foo = () -> "";
    }
    @FunctionalInterface
    interface FunctionWithOneParameter<One> {
        public One apply(One one);
    }

    /**
     * Example 13: internal lambda hides a field
     */
    Double mPi = Math.PI;
    List<Double> simpleNumbers = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
    {
        simpleNumbers.forEach(digit -> {
            FunctionWithOneParameter<Double> strangeAdder = (mPi -> mPi+= digit); // 1 violation
        });
    }

    @FunctionalInterface
    interface FunctionWithComplexGenerics<One, Two> {
        public Two foo(One one, Two two);
    }

    /**
     * Example 14: lambda typed with complex generics
     */
    List<Double> justSomeList;
    Map<String, Object> justSomeMap;
    {
        FunctionWithComplexGenerics<List<Double>, Map<String, Object>> someWierdFunc =
            (List<Double> justSomeList, Map<String, Object> justSomeMap) -> { // 2 violations
                System.out.println(justSomeList);
                System.out.println(justSomeMap);
                return new HashMap<>();
            };
    }

    /**
     * Example 15: lambda stored in field (with typed parameter)
     * hides other field
     */
    Object someObject = new Object();
    FunctionWithOneParameter objectToString = (Object someObject) -> { // 1 violation
        return someObject.toString();
    };

    /**
     * Example 16: lambda stored in field (with untyped parameter)
     * hides other field
     */
    FunctionWithOneParameter otherObjectToString = someObject -> { // 1 violation
        return someObject.toString();
    };
}
