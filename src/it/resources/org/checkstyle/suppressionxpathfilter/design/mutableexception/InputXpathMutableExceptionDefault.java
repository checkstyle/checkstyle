/*
MutableException
extendedClassNameFormat = (default)^.*Exception$|^.*Error$|^.*Throwable$
format = (default)^.*Exception$|^.*Error$|^.*Throwable$


*/

package org.checkstyle.suppressionxpathfilter.design.mutableexception;

public class InputXpathMutableExceptionDefault {
    public class FooException extends Exception {
        // violation below 'The field 'code' must be declared final.'
        private int code;

        public FooException() {
            code = 2;
        }
    }
}
