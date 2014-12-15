package com.puppycrawl.tools.checkstyle.design;

public class InputMutableException {
    public class FooException extends Exception {
        private final int _finalErrorCode;
        private int _errorCode = 1;

        public FooException() {
            _finalErrorCode = 1;
        }

        public class FooExceptionThisIsNot extends RuntimeException {
            private final int _finalErrorCode;
            private int _errorCode = 1;
            /** constructor */
            public FooExceptionThisIsNot() {
                _finalErrorCode = 1;
            }
        }
    }

    public class FooError extends Error {
        private int _errorCode;
    }

    public class BarDoesNotExtendError {
        private int _errorCode;
    }
}
