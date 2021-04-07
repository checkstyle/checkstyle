////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config: default
 */
public class InputLeftCurlyTestDefault
{ // violation
    private interface PrivateInterface
    { // violation
    }

    interface PackageInnerInterface
    { // violation
    }

    protected interface ProtectedInnerInterface
    { // violation
    }

    public interface PublicInnerInterface
    { // violation
    }

    private
    class
    MyClass1 { // ok
    }

    class
    MyClass2 { // ok
    }

    private
    interface
    MyInterface1 { // ok
    }

    interface
    MyInterface2 { // ok
    }

    protected
    enum
    MyEnum { // ok
    }

    private
    @interface
    MyAnnotation { // ok
    }

    @Deprecated
    public
    class MyClass3 { // ok
    }

    public class MyClass4 { // ok
        void method() { // ok
            while(true) {/*foo*/} // ok
        }
    }
}
