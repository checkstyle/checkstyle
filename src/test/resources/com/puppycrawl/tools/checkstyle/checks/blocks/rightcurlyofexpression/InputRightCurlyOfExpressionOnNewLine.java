/*
RightCurlyOfExpression
option = on_new_line


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyofexpression;

public class InputRightCurlyOfExpressionOnNewLine {

    String[] multiLineArrayOk = new String[] {"One", "Two",
    };

    String[] singleLineArrayViolation = new String[] {"One", "Two", };
    // violation above ''}' at column 69 should be on a new line'

    String[] multiLineArrayViolation = new String[] {"One",
        "Two", };
    // violation above ''}' at column 16 should be on a new line'

    String[] emptyArrayViolation = new String[] {};
    // violation above ''}' at column 50 should be on a new line'

    Runnable multiLineLambdaOk = () -> {
        doSomething();
    };

    Runnable singleLineLambdaViolation = () -> { doSomething(); };
    // violation above ''}' at column 65 should be on a new line'

    Runnable expressionLambda = () -> doSomething();

    Object multiLineAnonOk = new Object() {
        int b = 10;
    };

    Object singleLineAnonViolation = new Object() { int b = 10; };
    // violation above ''}' at column 65 should be on a new line'

    Object plainNew = new Object();

    void doSomething() {
    }
}
