/*
MutableException
extendedClassNameFormat = ^.*Throwable$
format = (default)^.*Exception$|^.*Error$|^.*Throwable$


*/

package org.checkstyle.suppressionxpathfilter.design.mutableexception;

public class InputXpathMutableExceptionExtendedClassName {
    public class FooException extends Throwable {
        private final int finalErrorCode;
        // violation below 'The field 'code' must be declared final.'
        private int code = 1;

        public FooException() {
            finalErrorCode = 1;
        }
    }
}
