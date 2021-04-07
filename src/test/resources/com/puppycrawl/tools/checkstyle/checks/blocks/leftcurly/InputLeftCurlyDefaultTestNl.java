package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = nl
 */
public class InputLeftCurlyDefaultTestNl
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
    MyClass1 { // violation
    }

    class
    MyClass2 { // violation
    }

    private
    interface
    MyInterface1 { // violation
    }

    interface
    MyInterface2 { // violation
    }

    protected
    enum
    MyEnum { // violation
    }

    private
    @interface
    MyAnnotation { // violation
    }

    @Deprecated
    public
    class MyClass3 { // violation
    }

    public class MyClass4 { // violation
        void method() { // violation
            while(true) {/*foo*/} // violation
        }
    }
}
