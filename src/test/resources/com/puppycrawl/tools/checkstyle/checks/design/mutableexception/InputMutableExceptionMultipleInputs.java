package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

public class InputMutableExceptionMultipleInputs {

    public class BarError extends Throwable {
        private int errorCode;
    }

    class CustomMutableException extends java.lang.Exception {
        int errorCode;
        final int errorCount = 6;
    }

    private String variable;
}
