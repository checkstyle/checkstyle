////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = invalid_option
 */
public class InputLeftCurlyTestInvalidOption
{ // ok
    private interface PrivateInterface
    { // ok
    }

    interface PackageInnerInterface
    { // ok
    }

    protected interface ProtectedInnerInterface
    { // ok
    }

    public interface PublicInnerInterface
    { // ok
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
