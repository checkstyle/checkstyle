/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT

*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.lang.Integer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class InputHiddenFieldLambdasInMethods {

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
        acceptableNotes.forEach(note -> String.valueOf(note));
        // violation above ''note' hides a field'
    }

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
        Function <String, Integer> m = (String stringValue, Integer intValue) -> {
        // 2 violations above:
        //    ''stringValue' hides a field'
        //    ''intValue' hides a field'
            return Integer.parseInt(stringValue) + intValue;
        };
        String.valueOf(m.apply ("22.4", 2));
    }

    /**
     * Example 11: typed parameters - one hide field
     */
    Double doubleValue = 8.5;
    {
        Function <Integer, Double> a =(Integer integerValue, Double doubleValue) -> {
        // violation above ''doubleValue' hides a field'
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
        Function <String, String> stringConcat = (firstString, secondString) -> {
        // 2 violations above:
        //    ''firstString' hides a field'
        //    ''secondString' hides a field'
            return firstString + secondString;
        };
        String.valueOf(stringConcat.apply("A", "B"));
    }
}
