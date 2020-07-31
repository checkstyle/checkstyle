//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

/*
 * Config:
 * applyToPublic = false
 */
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
