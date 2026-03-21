/*
MutableException
format = (default)^.*Exception$|^.*Error$|^.*Throwable$
extendedClassNameFormat = (default)^.*Exception$|^.*Error$|^.*Throwable$


*/

package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

public class InputMutableExceptionMultipleInputs {

    public class BarError extends Throwable {
        private int errorCode; // violation 'The field 'errorCode' must be declared final'
    }

    class CustomMutableException extends java.lang.Exception {
        int errorCode; // violation 'The field 'errorCode' must be declared final'
        final int errorCount = 6;
    }

    private String variable;
}
