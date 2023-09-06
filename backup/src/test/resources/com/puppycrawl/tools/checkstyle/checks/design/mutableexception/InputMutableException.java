/*
MutableException
format = (default)^.*Exception$|^.*Error$|^.*Throwable$
extendedClassNameFormat = (default)^.*Exception$|^.*Error$|^.*Throwable$


*/

package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

public class InputMutableException {
    public class FooException extends Exception {
        private final int finalErrorCode;
        private int errorCode = 1; // violation

        public FooException() {
            finalErrorCode = 1;
        }

        public class FooExceptionThisIsNot extends RuntimeException {
            private final int finalErrorCode;
            private int errorCode = 1;
            /** constructor */
            public FooExceptionThisIsNot() {
                finalErrorCode = 1;
            }
        }
    }

    public class BarError extends Throwable {
        private int errorCode; // violation
    }

    public class BazDoesNotExtendError {
        private int errorCode;
    }

    public class CustomProblem extends ThreadDeath {
        private int errorCode;

        public class CustomFailure extends ThreadDeath {
            private int errorCode;
            public void someMethod() {
                if(true) {
                    final int i = 0;
                }
            }
        }
    }

    class CustomException extends java.lang.Exception {}

    class CustomMutableException extends java.lang.Exception {
        int errorCode; // violation
    }

    class ExampleException extends java.lang.Exception {
        public void test() {
            Throwable cause = super.getCause();
            if (!(cause instanceof java.io.IOException))
                throw new IllegalStateException("Test");
        }
    }
}
