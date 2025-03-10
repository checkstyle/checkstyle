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

    default void DefaultMethod() { // violation 'Name 'DefaultMethod' must match pattern'
    }

    public default void DefaultMethod2() { // violation 'Name 'DefaultMethod2' must match pattern'
    }

    void PublicMethod(); // violation 'Name 'PublicMethod' must match pattern'

    public void PublicMethod2(); // violation 'Name 'PublicMethod2' must match pattern'

}
