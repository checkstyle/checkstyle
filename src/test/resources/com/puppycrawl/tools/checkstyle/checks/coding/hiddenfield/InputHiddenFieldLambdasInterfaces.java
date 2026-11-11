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


public class InputHiddenFieldLambdasInterfaces {

    @FunctionalInterface
    interface SomeFunction<One, Two> {
        public Two apply(One one, Two two);
    }

    /**
     * Example 11: untyped parameters - one hide field
     */
    Integer first = 1;
    {
        Function<Integer, Character> turnToZ = (first, second) -> 'z';
        // violation above ''first' hides a field'
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
            FunctionWithOneParameter<Double> strangeAdder = (mPi -> mPi+= digit);
            // violation above ''mPi' hides a field'
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
            (List<Double> justSomeList, Map<String, Object> justSomeMap) -> {
            // 2 violations above:
            //    ''justSomeList' hides a field'
            //    ''justSomeMap' hides a field'
                String.valueOf(justSomeList);
                String.valueOf(justSomeMap);
                return new HashMap<>();
            };
    }

    /**
     * Example 15: lambda stored in field (with typed parameter)
     * hides other field
     */
    Object someObject = new Object();
    FunctionWithOneParameter objectToString = (Object someObject) -> {
    // violation above ''someObject' hides a field'
        return someObject.toString();
    };

    /**
     * Example 16: lambda stored in field (with untyped parameter)
     * hides other field
     */
    FunctionWithOneParameter otherObjectToString = someObject -> {
    // violation above ''someObject' hides a field'
        return someObject.toString();
    };

    private final String l = "";
    private interface NestedInterface {
        void print(String l);
    }

    @FunctionalInterface
    interface Function <A, B> {
        public B apply (A a, B b);
    }
}
