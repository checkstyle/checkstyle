/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = false
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

public interface InputMethodNamePublicMethodsInInterfaces {

    private void PrivateMethod() {} // violation

    private static void PrivateMethod2() {} // violation

    default void DefaultMethod() { // ok
    }

    public default void DefaultMethod2() { // ok
    }

    void PublicMethod(); // ok

    public void PublicMethod2(); // ok

}
