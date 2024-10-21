package org.checkstyle.suppressionxpathfilter.mutableexception;

public class InputXpathMutableExceptionDefault {
    public class FooException extends Exception {
        private int code; // warn

        public FooException() {
            code = 2;
        }
    }
}
