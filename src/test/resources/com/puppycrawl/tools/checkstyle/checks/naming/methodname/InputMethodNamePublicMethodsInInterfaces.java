/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = false
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/


package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

public interface InputMethodNamePublicMethodsInInterfaces {
    // violation below 'Method name 'PrivateMethod' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    private void PrivateMethod() {}

    // violation below 'Method name 'PrivateMethod2' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    private static void PrivateMethod2() {}

    default void DefaultMethod() {
    }

    public default void DefaultMethod2() {
    }

    void PublicMethod();

    public void PublicMethod2();

}
