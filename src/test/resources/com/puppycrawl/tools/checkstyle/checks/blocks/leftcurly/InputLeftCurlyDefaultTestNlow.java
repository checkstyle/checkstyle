package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = nlow
 */
public class InputLeftCurlyDefaultTestNlow
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

    public class MyClass4 { // ok
        void method() { // ok
            while(true) {/*foo*/} // ok
        }
    }
}
