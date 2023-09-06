/*
MutableException
format = (default)^.*Exception$|^.*Error$|^.*Throwable$
extendedClassNameFormat = (default)^.*Exception$|^.*Error$|^.*Throwable$


*/

package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

public class InputMutableExceptionNested {
    class Example2Exception extends java.lang.Exception {
        class NormalClass {}

        private int code; // violation 'The field 'code' must be declared final'
    }
}
