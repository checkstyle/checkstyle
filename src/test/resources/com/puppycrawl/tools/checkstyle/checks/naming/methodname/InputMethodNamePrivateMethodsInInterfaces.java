/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = false


*/


package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

public interface InputMethodNamePrivateMethodsInInterfaces {

    private void PrivateMethod() {}

    private static void PrivateMethod2() {}

    // violation 'DefaultMethod' must match the pattern
    default void DefaultMethod() {
    }

    // violation 'DefaultMethod2' must match the pattern
    public default void DefaultMethod2() {
    }

    // violation 'PublicMethod' must match the pattern
    void PublicMethod();

    // violation 'PublicMethod2' must match the pattern
    public void PublicMethod2();

}
