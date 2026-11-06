/*
RightCurlyOfExpression
option = (default)on_new_line_or_singleline


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyofexpression;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InputRightCurlyOfExpressionDefault {

    String[] singleLineArray = new String[] {"One", "Two", };

    String[] multiLineArrayOk = new String[] {"One", "Two",
    };

    String[] multiLineArrayViolation = new String[] {"One",
        "Two", };
    // violation above ''}' at column 16 should be on a new line'

    String[] noNewArray = {"One", "Two", };

    int[][] multiDimOk = { {1, 2}, {3, 4} };

    int[][] multiDimViolation = { {1, 2},
        {3, 4} };
    // violation above ''}' at column 16 should be on a new line'

    int[][] multiDimNewLine = { {1, 2},
        {3, 4}
    };

    int[][] multiDimInnerSplit = { {1,
        2}, {3, 4}
    };

    String[] emptyArray = new String[] {};

    Object streamResult = Arrays.stream(new String[] {"Test", "Before",
        "BeforeClass", "AfterClass", }).collect(Collectors.toSet());
    // violation above ''}' at column 38 should be on a new line'

    Runnable singleLineLambda = () -> { doSomething(); };

    Runnable multiLineLambdaOk = () -> {
        doSomething();
    };

    Runnable multiLineLambdaViolation = () -> {
        doSomething(); };
    // violation above ''}' at column 24 should be on a new line'

    Runnable expressionLambda = () -> doSomething();

    Runnable emptyLambda = () -> {};

    Runnable nestedLambda = () -> {
        Runnable inner = () -> { doSomething(); };
        inner.run();
    };

    Object singleLineAnon = new Object() { int b = 10; };

    Object multiLineAnonOk = new Object() {
        int b = 10;
    };

    Object multiLineAnonViolation = new Object() {
        int b = 10; };
    // violation above ''}' at column 21 should be on a new line'

    Object plainNew = new Object();

    Object emptyAnon = new Object() {};

    Object doubleBrace = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second"); }
        };

    void doSomething() {
    }

    void methodArgLambda() {
        java.util.function.Supplier<String> supplier = () -> {
            return "value";
        };
        supplier.get();
    }
}
