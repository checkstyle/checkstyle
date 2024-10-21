package org.checkstyle.suppressionxpathfilter.mutableexception;

public class InputXpathMutableExceptionExtendedClassName {
    public class FooException extends Throwable {
        private final int finalErrorCode;
        private int code = 1; // warn

        public FooException() {
            finalErrorCode = 1;
        }
    }
}
