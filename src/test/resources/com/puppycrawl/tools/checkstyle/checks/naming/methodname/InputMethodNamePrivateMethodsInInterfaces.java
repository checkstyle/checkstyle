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

    default void DefaultMethod() { // violation
    }

    public default void DefaultMethod2() { // violation
    }

    void PublicMethod(); // violation

    public void PublicMethod2(); // violation

}
