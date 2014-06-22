package com.puppycrawl.tools.checkstyle.checks.design;

public class InputMutableException {
    public class FooException {
        private final int _finalErrorCode;
        private int _errorCode = 1;

        public FooException() {
            _finalErrorCode = 1;
        }

        public class FooExceptionThisIsNot {
            private final int _finalErrorCode;
            private int _errorCode = 1;
            /** constructor */
            public FooExceptionThisIsNot() {
                _finalErrorCode = 1;
            }
        }
    }

    public class FooError {
        private int _errorCode;
    }
}
