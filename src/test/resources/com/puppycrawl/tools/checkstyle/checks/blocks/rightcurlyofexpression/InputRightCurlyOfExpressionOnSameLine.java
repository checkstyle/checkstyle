/*
RightCurlyOfExpression
option = on_same_line


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyofexpression;

public class InputRightCurlyOfExpressionOnSameLine {

    String[] singleLineArray = new String[] {"One", "Two", };

    String[] multiLineArrayOk = new String[] {"One",
        "Two", };

    String[] multiLineArrayViolation = new String[] {"One", "Two",
    };
    // violation above ''}' at column 5 should be on the previous line'

    int[][] multiDimViolation = { {1, 2},
        {3, 4}
    };
    // violation above ''}' at column 5 should be on the previous line'

    String[] emptyArray = new String[] {};

    String[] emptyArraySplit = new String[] {
    };
    // violation above ''}' at column 5 should be on the previous line'

    Runnable singleLineLambda = () -> { doSomething(); };

    Runnable multiLineLambdaOk = () -> { doSomething();
        doSomething(); };

    Runnable multiLineLambdaViolation = () -> {
        doSomething();
    };
    // violation above ''}' at column 5 should be on the previous line'

    Runnable expressionLambda = () -> doSomething();

    Object singleLineAnon = new Object() { int b = 10; };

    Object multiLineAnonOk = new Object() { int b = 10;
        int c = 20; };

    Object multiLineAnonViolation = new Object() {
        int b = 10;
    };
    // violation above ''}' at column 5 should be on the previous line'

    Object plainNew = new Object();

    void doSomething() {
    }
}
