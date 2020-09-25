//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

/*
 * Config:
 * applyToPrivate = false
 */
public interface InputMethodNamePrivateMethodsInInterfaces {

    private void PrivateMethod() {} // ok

    private static void PrivateMethod2() {} // ok

    default void DefaultMethod() { // violation
    }

    public default void DefaultMethod2() { // violation
    }

    void PublicMethod(); // violation

    public void PublicMethod2(); // violation

}
