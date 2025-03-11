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

    // violation below 'Method name 'DefaultMethod' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    default void DefaultMethod() {
    }

    // violation below 'Method name 'DefaultMethod2' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    public default void DefaultMethod2() {
    }

    // violation below 'Method name 'PublicMethod' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    void PublicMethod();

    // violation below 'Method name 'PublicMethod2' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    public void PublicMethod2();

}
