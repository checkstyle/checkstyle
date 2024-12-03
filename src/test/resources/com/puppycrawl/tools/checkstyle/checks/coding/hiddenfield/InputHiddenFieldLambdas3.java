package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputHiddenFieldLambdas3 {

    
   
    /**
     * Example 9: lambda parameter 'letter' on line 109
     * does not hide a field 'letter' on line 106, because
     * field 'letter' can not be referenced from a static context.
     */
    String letter = new String("a");
    private static void foo5() {
        List<String> acceptableAlphabet = Arrays.asList("a", "b", "c");
        acceptableAlphabet.forEach(letter -> String.valueOf(letter));
    }

   

    /**
     * Example 10: typed parameters - two hide fields
     */
    String stringValue = "248.3";
    int intValue = 2;
    {
        Function <String, Integer> m = (String stringValue, Integer intValue) -> { // 2 violations
            return Integer.parseInt(stringValue) + intValue;
        };
        String.valueOf(m.apply ("22.4", 2));
    }

    /**
     * Example 11: typed parameters - one hide field
     */
    Double doubleValue = 8.5;
    {
        Function <Integer, Double> a =(Integer integerValue, Double doubleValue) -> { // violation
            return  integerValue + doubleValue;
        };
        String.valueOf(a.apply(2, 2.2));
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
        String.valueOf(stringConcat.apply("A", "B"));
    }

    /**
     * Example 11: untyped parameters - one hide field
     */
    Integer first = 1;
    {
        Function<Integer, Character> turnToZ = (first, second) -> 'z'; // violation
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
            FunctionWithOneParameter<Double> strangeAdder = (mPi -> mPi+= digit); // violation
        });
    }

   
}