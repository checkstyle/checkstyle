package com.puppycrawl.tools.checkstyle.design;

public class InputMutableException {
    public class FooException extends Exception {
        private final int finalErrorCode;
        private int errorCode = 1;

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
        private int errorCode;
    }

    public class BazDoesNotExtendError {
        private int errorCode;
    }

    public class CustomProblem extends ThreadDeath {
        private int errorCode;

        public class CustomFailure extends ThreadDeath {
            private int errorCode;
        }
    }
}
